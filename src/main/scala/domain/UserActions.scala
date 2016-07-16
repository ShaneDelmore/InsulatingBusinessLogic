package domain

import java.time.{Duration, Instant}

import cats.implicits._
//I could import specific methods only if I wanted
//import cats.syntax.flatMap._
//import cats.syntax.functor._
import repos._

case class TokenLifetime(createdAt: Instant, expiresAt: Instant)
case class Notification(message: String)
case class AuthToken(value: String, lifetime: TokenLifetime)
case class Credentials(username: String, password: String)
case class User(username: String)

trait UserActions[F[_]] { this: UserRepo[F] =>
  def service: UserActions = new UserActions
  class UserActions {
    def login(creds: Credentials, ttl: Duration) =
      for {
        loginTtl <- repo.validatedTokenLifetime
        token <- repo.loginUser(creds, loginTtl)
        user <- repo.findUser(token)
        notifications <- repo.getUserNotifications(user)
      } yield (token, notifications)
  }

}

