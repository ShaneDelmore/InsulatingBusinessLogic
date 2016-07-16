package domain

object UserMessages {
  sealed abstract class UserMessage {
    val message: String
  }
  case class InvalidTokenDuration(message: String) extends UserMessage

  case object LoginFailed extends UserMessage {
    val message = "Your login attempt failed"
  }

  case object UserNotFound extends UserMessage {
    val message = "User account could not be located"
  }

  case object UserAccountSuspended extends UserMessage {
    val message = "The user account has been suspended"
  }
}
