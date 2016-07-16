package repos

import cats.Monad
import cats.data.{Xor, XorT}
import cats.implicits._
import domain.StubValues._
import domain.UserMessages.UserMessage
import domain._
import repos.XorT_Types.UserMessageOrT

import scala.concurrent.{ExecutionContext, Future}

object XorT_Types {
  type UserMessageOrT[A] = XorT[Future, UserMessage, A]
}

class FutureXorRepo(implicit ec: ExecutionContext) extends UserRepo[UserMessageOrT] {
  override val F = implicitly[Monad[UserMessageOrT]]
  override val repo: Repo = new FutureXorRepo {}

  trait FutureXorRepo extends Repo {
    def validatedTokenLifetime: UserMessageOrT[TokenLifetime] =
      XorT.fromXor(Xor.Right(stubLifetime))

    def loginUser(creds: Credentials, lifetime: TokenLifetime): UserMessageOrT[AuthToken] =
      XorT.fromXor(Xor.Right(stubToken))

    def findUser(token: AuthToken): UserMessageOrT[User] =
      XorT.fromXor(Xor.Right(stubUser))

    def getUserNotifications(user: User): UserMessageOrT[List[Notification]] =
      XorT.fromXor(Xor.Right(stubNotifications))
  }
}

