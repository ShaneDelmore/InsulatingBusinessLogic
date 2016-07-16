package repos

import cats.Monad
import cats.data.Xor
import domain.UserMessages._

object XorTypes {
  type UserMessageOr[A] = Xor[UserMessage, A]
}

import XorTypes.UserMessageOr

class XorRepo extends UserRepo[UserMessageOr] {
  override val F = implicitly[Monad[UserMessageOr]]
  override val repo: Repo = new UserRepo0 {}
  trait UserRepo0 extends Repo {
    def validatedTokenLifetime: UserMessageOr[TokenLifetime] = ???
    def loginUser(creds: Credentials, lifetime: TokenLifetime): UserMessageOr[AuthToken] = ???
    def findUser(token: AuthToken): UserMessageOr[User] = ???
    def getUserNotifications(user: User): UserMessageOr[List[Notification]] = ???
  }
}

