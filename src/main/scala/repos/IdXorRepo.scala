package repos

import cats.Monad
import cats.data.{Xor, XorT}
import domain.StubValues._
import domain._
import repos.XorTAliases.UserMessageOrTId

class IdXorRepo extends UserRepo[UserMessageOrTId] {
  override val F = implicitly[Monad[UserMessageOrTId]]
  override val repo: Repo = new IdXorRepo {}

  trait IdXorRepo extends Repo {
    def validatedTokenLifetime: UserMessageOrTId[TokenLifetime] =
      XorT.fromXor(Xor.Right(stubLifetime))

    def loginUser(creds: Credentials, lifetime: TokenLifetime): UserMessageOrTId[AuthToken] =
      XorT.fromXor(Xor.Right(stubToken))

    def findUser(token: AuthToken): UserMessageOrTId[User] =
      XorT.fromXor(Xor.Right(stubUser))

    def getUserNotifications(user: User): UserMessageOrTId[List[Notification]] =
      XorT.fromXor(Xor.Right(stubNotifications))
  }
}

