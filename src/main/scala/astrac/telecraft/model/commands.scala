package astrac.telecraft.model

import DataId._

trait Cmd[Req, Resp] {
  def uri: String
}

sealed trait Command

object Command {
  implicit val sendMessageIsCommand: Cmd[SendMessage, Message] = new Cmd[SendMessage, Message] { val uri = "sendMessage" }
  // TODO: Add typeclass instances for all commands
  // implicit val getMeIsCommand: Cmd[GetMe.type, User] = ???
}

case object GetMe extends Command

case class SendMessage(
  chatId: ChatId,
  text: String,
  parseMode: Option[String] = None,
  disableWebPagePreview: Option[Boolean] = None,
  replyToMessageId: Option[Long] = None,
  replyMarkup: Option[ReplyMarkup] = None
) extends Command
