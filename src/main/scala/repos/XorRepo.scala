package repos

import cats.Monad
import cats.data.Xor
import domain._
import domain.UserMessages._
import domain.StubValues._

object XorTypes {
  type UserMessageOr[A] = Xor[UserMessage, A]
}

import XorTypes.UserMessageOr

class XorRepo extends UserRepo[UserMessageOr] {
  override val F = implicitly[Monad[UserMessageOr]]
  override val repo: Repo = new XorRepo {}

  trait XorRepo extends Repo {
    def validatedTokenLifetime: UserMessageOr[TokenLifetime] =
      Xor.Right(stubLifetime)

    def loginUser(creds: Credentials, lifetime: TokenLifetime): UserMessageOr[AuthToken] =
      Xor.Right(stubToken)

    def findUser(token: AuthToken): UserMessageOr[User] =
      Xor.Right(stubUser)

    def getUserNotifications(user: User): UserMessageOr[List[Notification]] =
      Xor.Right(stubNotifications)
  }
}

