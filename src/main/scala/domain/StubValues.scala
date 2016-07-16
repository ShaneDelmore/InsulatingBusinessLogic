package domain

import java.time.Instant

object StubValues {
  val stubLifetime =
    TokenLifetime(Instant.now, Instant.now.plusSeconds(60L))

  val stubToken = AuthToken("aToken", stubLifetime)

  val stubUser = User("Sunny")

  val stubNotifications = List(Notification("Welcome back, it's been a while since you logged in."))

}
