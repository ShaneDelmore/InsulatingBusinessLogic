package domain

import java.time.Duration

import org.scalatest.{AsyncFunSpec, FunSpec, Matchers}
import repos._
import StubValues._
import cats.data.Xor
import cats.implicits._

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
      val subject = new XorRepo with UserActions[XorTypes.UserMessageOr]
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
      val subject = new FutureXorRepo with UserActions[XorT_Types.UserMessageOrT]
      //Slight tweak here only because I do not have a matcher for XorT in AsyncFunSpec
      subject.service.login(testCredentials, ttl).value.map{
        result => result shouldBe Xor.Right(stubToken, stubNotifications)
      }
    }
  }


}
