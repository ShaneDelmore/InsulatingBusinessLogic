package repos

import java.time.Instant

import cats.Monad
import cats.implicits._

class OptionalRepo extends UserRepo[Option] {
  override val F = implicitly[Monad[Option]]
  override val repo: Repo = new UserRepo0 {}
  trait UserRepo0 extends Repo {
    def validatedTokenLifetime: Option[TokenLifetime] =
      Option(TokenLifetime(Instant.now, Instant.now.plusSeconds(60L)))

    def loginUser(creds: Credentials, lifetime: TokenLifetime): Option[AuthToken] =
      Option(AuthToken("aToken", lifetime))

    def findUser(token: AuthToken): Option[User] =
      Option(User("Sunny"))

    def getUserNotifications(user: User): Option[List[Notification]] =
      Option(List(Notification("Welcome back, it's been a while since you logged in.")))
  }
}

