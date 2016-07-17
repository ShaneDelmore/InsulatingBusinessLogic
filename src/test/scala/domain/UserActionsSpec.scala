package domain

import java.time.Duration

import repos._
import StubValues._
import util.AsyncSpec
import XorTAliases.UserMessageOrTFuture
import cats.implicits._
import repos.XorTypes.UserMessageOr

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class UserActionsSpec extends AsyncSpec
{

  val testCredentials = Credentials("Sunny", "topSecret")
  val ttl = Duration.ofDays(14L)

  describe("UserActions") {
      it("works with options") {
        val service = (new OptionalRepo with UserActions[Option])
        service.login(testCredentials, ttl).map(result =>
          result shouldBe (stubToken, stubNotifications)
        )
      }

    it("works with Xor") {
      val service = (new XorRepo with UserActions[UserMessageOr])
      service.login(testCredentials, ttl).map(result =>
        result shouldBe (stubToken, stubNotifications)
      )
    }

    it("works with XorT") {
      val service = (new IdXorRepo with UserActions[XorTAliases.UserMessageOrTId])
      service.login(testCredentials, ttl).map(result =>
        result shouldBe (stubToken, stubNotifications)
      )
    }

    it("works with Future") {
      val service = (new FutureRepo with UserActions[Future])
      service.login(testCredentials, ttl).map(result =>
        result shouldBe (stubToken, stubNotifications)
      )
    }

    it("works with FutureXor") {
      val service = (new FutureXorRepo with UserActions[UserMessageOrTFuture])
      service.login(testCredentials, ttl).map(result =>
        result shouldBe (stubToken, stubNotifications)
      )
    }
  }
}

