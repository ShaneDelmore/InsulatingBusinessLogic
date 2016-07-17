package util

import language.implicitConversions
import cats._
import cats.data._
import cats.implicits._
import org.scalatest.{Assertions, _}
import repos.XorTAliases
import repos.XorTAliases._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object AsyncSpec extends AsyncSpec

//AsyncSpec includes AsyncFunSpec plus helpers to allow Synchronous specs to be written in a similar style
trait AsyncSpec
  extends AsyncFunSpec
  with Matchers {
  lazy val unexpectedLeftSideEncountered =
    Assertions.fail("Assertion made against right side but encountered left side")

  implicit def toFuture(a: Assertion): Future[Assertion] = Future.successful(a)

  //Add toFuture to assertion to be a little more evident how I am doing conversions here.
  implicit class AssertionImprovements(assertion: Assertion) {
    def toFuture = Future.successful(assertion)
  }

  //This allows me to use Sync Assertions in tests expecting Async Assertions
  implicit def assertionToFuture(assertion: Assertion): Future[Assertion] =
    assertion.toFuture

  implicit def optionAssertionToFutureAssertion(opt: Option[Assertion]): Future[Assertion] =
    opt.get.toFuture

  implicit def xorAssertionToFutureAssertion[A](xor: Xor[A, Assertion]): Future[Assertion] =
    xor.getOrElse(unexpectedLeftSideEncountered).toFuture

  implicit def xorTIdtoFuture(xorT: XorTAliases.UserMessageOrTId[Assertion]): Future[Assertion] =
    xorT.getOrElse(unexpectedLeftSideEncountered).toFuture

  implicit def xorTtoFuture(xorT: UserMessageOrTFuture[Assertion]): Future[Assertion] =
    xorT.getOrElse(unexpectedLeftSideEncountered)
}
