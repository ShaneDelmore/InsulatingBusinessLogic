package repos

import domain._
import cats.Monad

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

