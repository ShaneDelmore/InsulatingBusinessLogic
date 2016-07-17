package repos

import language.higherKinds
import domain._
import cats.Monad

trait UserRepo[F[_]] {
  //Need implicit evidence to convince the compiler that F has a flatMap
  implicit val evidence: Monad[F]
  def validatedTokenLifetime: F[TokenLifetime]
  def loginUser(creds: Credentials, lifetime: TokenLifetime): F[AuthToken]
  def findUser(token: AuthToken): F[User]
  def getUserNotifications(user: User): F[List[Notification]]
}

