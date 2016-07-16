package repos

import cats.Monad
import cats.implicits._
import domain.StubValues._
import domain._

import scala.concurrent.{ExecutionContext, Future}

class FutureRepo(implicit ec: ExecutionContext) extends UserRepo[Future] {
  override val F = implicitly[Monad[Future]]
  override val repo: Repo = new FutureRepo {}

  trait FutureRepo extends Repo {
    def validatedTokenLifetime: Future[TokenLifetime] =
      Future(stubLifetime)

    def loginUser(creds: Credentials, lifetime: TokenLifetime): Future[AuthToken] =
      Future(stubToken)

    def findUser(token: AuthToken): Future[User] =
      Future(stubUser)

    def getUserNotifications(user: User): Future[List[Notification]] =
      Future(stubNotifications)
  }
}

