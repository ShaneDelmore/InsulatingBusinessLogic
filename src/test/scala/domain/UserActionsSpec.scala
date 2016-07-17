package domain

import java.time.Duration

import org.scalatest.{Assertion, AsyncFunSpec, FunSpec, Matchers}
import repos._
import StubValues._
import XorT_Types.UserMessageOrT
import cats.data.{Xor, XorT}
import cats.implicits._
import repos.XorTypes.UserMessageOr

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class UserActionsSpec
  extends FunSpec
  with Matchers
{
  val testCredentials = Credentials("Sunny", "topSecret")
  val ttl = Duration.ofDays(14L)

  describe("UserActions") {
    it("works with options") {
      val subject = new OptionalRepo with UserActions[Option]
      subject.service.login(testCredentials, ttl).map{
        result => result shouldBe (stubToken, stubNotifications)
      }
    }

    it("works with Xor") {
      val subject = new XorRepo with UserActions[UserMessageOr]
      subject.service.login(testCredentials, ttl).map{
        result => result shouldBe (stubToken, stubNotifications)
      }
    }

    it("works with XorT") {
      val subject = new IdXorRepo with UserActions[XorT_Id_Types.UserMessageOrT]
      subject.service.login(testCredentials, ttl).map{
        result => result shouldBe (stubToken, stubNotifications)
      }
    }
  }
}

class UserActionsAsyncSpec
  extends AsyncFunSpec
    with Matchers
{
  import EnrichedXorT._

  val testCredentials = Credentials("Sunny", "topSecret")
  val ttl = Duration.ofDays(14L)

  describe("UserActions") {
    it("works with Future") {
      val subject = new FutureRepo with UserActions[Future]
      subject.service.login(testCredentials, ttl).map{
        result => result shouldBe (stubToken, stubNotifications)
      }
    }

    it("works with FutureXor") {
      val subject = new FutureXorRepo with UserActions[UserMessageOrT]
      subject.service.login(testCredentials, ttl).map{
        result => result shouldBe (stubToken, stubNotifications)
      }
    }
  }

  object EnrichedXorT {
    //Super hacky workaround for the fact that ScalaTest wants a Future[Assertion], not an XorT[Monad, A, Assertion]
    implicit def XorTtoFuture(xorT: UserMessageOrT[Assertion]): Future[Assertion] = {
      xorT.leftMap(_ => org.scalatest.Assertions.fail("Left side found")).merge
    }
  }

}
