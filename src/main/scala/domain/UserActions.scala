package domain

import java.time.{Duration, Instant}

import cats.Monad

import language.higherKinds
import cats.implicits._
import repos._

case class TokenLifetime(createdAt: Instant, expiresAt: Instant)
case class Notification(message: String)
case class AuthToken(value: String, lifetime: TokenLifetime)
case class Credentials(username: String, password: String)
case class User(username: String)

trait UserActions[F[_]] { this: UserRepo[F] =>
  def login(creds: Credentials, ttl: Duration) =
    for {
      loginTtl <- validatedTokenLifetime
      token <- loginUser(creds, loginTtl)
      user <- findUser(token)
      notifications <- getUserNotifications(user)
    } yield (token, notifications)
}

