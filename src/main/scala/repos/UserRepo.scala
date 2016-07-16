package repos

import java.time.Instant

import cats.Monad

case class TokenLifetime(createdAt: Instant, expiresAt: Instant)
case class Notification(message: String)
case class AuthToken(value: String, lifetime: TokenLifetime)
case class Credentials(username: String, password: String)
case class User(username: String)

trait UserRepo[F[_]] {
  implicit def F: Monad[F]
  def repo: Repo
  trait Repo {
    def validatedTokenLifetime: F[TokenLifetime]
    def loginUser(creds: Credentials, lifetime: TokenLifetime): F[AuthToken]
    def findUser(token: AuthToken): F[User]
    def getUserNotifications(user: User): F[List[Notification]]
  }
}

