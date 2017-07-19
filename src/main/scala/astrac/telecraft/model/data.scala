package astrac.telecraft.model

import shapeless.tag._

object DataId {
  sealed trait ChatIdType
  sealed trait MessageIdType
  sealed trait UserIdType
  sealed trait FileIdType

  type ChatId = Long @@ ChatIdType
  type MessageId = Long @@ MessageIdType
  type UserId = Long @@ UserIdType
  type FileId = Long @@ FileIdType
}

import DataId._

sealed trait FileContent { def fileId: FileId }

case class Audio(
  fileId: FileId,
  duration: Int,
  performer: Option[String],
  title: Option[String],
  mimeType: Option[String],
  fileSize: Option[Int]
) extends FileContent

case class Document(
  fileId: FileId,
  thumb: Option[Photo],
  fileName: Option[String],
  mimeType: Option[String],
  fileSize: Option[Int]
) extends FileContent

case class Photo(
  fileId: FileId,
  width: Int,
  height: Int,
  fileSize: Option[Int]
) extends FileContent

case class Sticker(
  fileId: FileId,
  width: Int,
  height: Int,
  thumb: Option[Photo],
  fileSize: Option[Int]
) extends FileContent

case class Video(
  fileId: FileId,
  width: Int,
  height: Int,
  duration: Int,
  thumb: Option[Photo],
  mimeType: Option[String],
  fileSize: Option[Int]
) extends FileContent

case class Voice(
  fileId: FileId,
  duration: Int,
  mimeType: Option[String],
  fileSize: Option[Int]
) extends FileContent

case class Contact(
  phoneNumber: String,
  firstName: String,
  lastName: Option[String],
  userId: Option[UserId]
)

case class Location(
  longitude: Float,
  latitude: Float
)

case class UserProfilePhotos(
  totalCount: Int,
  photos: Seq[Seq[Photo]]
)

case class File(
  id: FileId,
  fileSize: Option[Int],
  filePath: Option[String]
)

sealed trait ReplyMarkup

case class ReplyKeyboardMarkup(
  keyboard: Seq[Seq[String]],
  resizeKeyboard: Option[Boolean],
  oneTimeKeyboard: Option[Boolean],
  selective: Option[Boolean]
) extends ReplyMarkup

case class ReplyKeyboardHide(
  hideKeyboard: Boolean,
  selective: Option[Boolean]
) extends ReplyMarkup

// TODO: Make Message a sealed trait, have a set of case classes for each message type rather than a lot of Option fields
case class Message(
  messageId: MessageId,
  from: Option[User],
  date: Long,
  chat: Chat,
  // forwardFrom: Option[User],
  // forwardDate: Option[Long],
  // replyToMessage: Option[Message],
  text: Option[String]//,
  // audio: Option[Audio],
  // document: Option[Document],
  // photo: Option[Photo],
  // sticker: Option[Sticker],
  // video: Option[Video],
  // caption: Option[String],
  // contact: Option[Contact],
  // location: Option[Location],
  // newChatParticipant: Option[User],
  // leftChatParticipant: Option[User],
  // newChatTitle: Option[String],
  // newChatPhoto: Option[Photo],
  // deleteChatPhoto: Option[Boolean],
  // supergroupChatCreated: Option[Boolean],
  // channelChatCreated: Option[Boolean],
  // migrateToChatId: Option[Long],
  // migrateFromChatId: Option[Long]
)

case class User(
  id: UserId,
  firstName: String,
  lastName: Option[String],
  username: Option[String]
)

case class Chat(
  id: Long,
  `type`: ChatType,
  title: Option[String],
  username: Option[String],
  firstName: Option[String],
  lastName: Option[String]
)

sealed trait ChatType { def text: String }

case object Supergroup extends ChatType { val text = "supergroup" }
case object Channel extends ChatType { val text = "channel" }
case object Group extends ChatType { val text = "group" }
case object Private extends ChatType { val text = "private" }

object ChatType {
  val fromString: Map[String, ChatType] = Seq(Supergroup, Channel, Group, Private).map(t => (t.text, t)).toMap
}
