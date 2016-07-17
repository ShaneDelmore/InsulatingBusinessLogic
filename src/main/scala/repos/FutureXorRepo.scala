package repos

import cats.Monad
import cats.data.{Xor, XorT}
import cats.implicits._
import domain.StubValues._
import domain._
import repos.XorTAliases.UserMessageOrTFuture
import scala.concurrent.ExecutionContext


class FutureXorRepo(implicit ec: ExecutionContext) extends UserRepo[UserMessageOrTFuture] {
  override val F = implicitly[Monad[UserMessageOrTFuture]]
  override val repo: Repo = new FutureXorRepo {}

  trait FutureXorRepo extends Repo {
    def validatedTokenLifetime: UserMessageOrTFuture[TokenLifetime] =
      XorT.fromXor(Xor.Right(stubLifetime))

    def loginUser(creds: Credentials, lifetime: TokenLifetime): UserMessageOrTFuture[AuthToken] =
      XorT.fromXor(Xor.Right(stubToken))

    def findUser(token: AuthToken): UserMessageOrTFuture[User] =
      XorT.fromXor(Xor.Right(stubUser))

    def getUserNotifications(user: User): UserMessageOrTFuture[List[Notification]] =
      XorT.fromXor(Xor.Right(stubNotifications))
  }
}

