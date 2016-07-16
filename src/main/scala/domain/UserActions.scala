package domain

import java.time.Duration

import cats.Monad._
import repos._

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
