package repos

import domain._
import cats.Monad
import cats.implicits._
import domain.StubValues._

class OptionalRepo extends UserRepo[Option] {
  override val F = implicitly[Monad[Option]]
  override val repo: Repo = new OptionalRepo {}

  trait OptionalRepo extends Repo {
    def validatedTokenLifetime: Option[TokenLifetime] =
      Option(stubLifetime)

    def loginUser(creds: Credentials, lifetime: TokenLifetime): Option[AuthToken] =
      Option(stubToken)

    def findUser(token: AuthToken): Option[User] =
      Option(stubUser)

    def getUserNotifications(user: User): Option[List[Notification]] =
      Option(stubNotifications)
  }
}

