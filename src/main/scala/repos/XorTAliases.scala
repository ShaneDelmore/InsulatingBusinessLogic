package repos

import cats.Id
import cats.data.XorT
import domain.UserMessages.UserMessage

import scala.concurrent.Future

object XorTAliases {
  type UserMessageOrTId[A] = XorT[Id, UserMessage, A]
  type UserMessageOrTFuture[A] = XorT[Future, UserMessage, A]
}
