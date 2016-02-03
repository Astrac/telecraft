package astrac.telecraft.model

import DataId._

sealed trait Command[R]

case object GetMe extends Command[User]

case class SendMessage(
  chatId: ChatId,
  message: String,
  parseMode: Option[String] = None,
  disableWebPagePreview: Option[Boolean] = None,
  replyToMessageId: Option[Long] = None,
  replyMarkup: Option[ReplyMarkup] = None
) extends Command[Message]
