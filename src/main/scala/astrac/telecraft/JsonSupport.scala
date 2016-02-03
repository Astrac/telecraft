package astrac.telecraft

import model._
import play.api.data.validation.ValidationError
import play.api.libs.json._
import play.api.libs.functional.syntax._
import shapeless.tag
import tag._

trait JsonSupport {
  import DataId._

  implicit def taggedLongReads[T](implicit longDec: Reads[Long]): Reads[Long @@ T] = longDec.map(tag[T](_))

  implicit val replyKeyboardMarkupWrites: Writes[ReplyKeyboardMarkup] = (
    (__ \ "keyboard").write[Seq[Seq[String]]] and
    (__ \ "resize_keyboard").write[Option[Boolean]] and
    (__ \ "one_time_keyboard").write[Option[Boolean]] and
    (__ \ "selective").write[Option[Boolean]]
  )(unlift(ReplyKeyboardMarkup.unapply))

  implicit val replyKeyboardHideWrites: Writes[ReplyKeyboardHide] = (
    (__ \ "hide_keyboard").write[Boolean] and
    (__ \ "selective").writeNullable[Boolean]
  )(unlift(ReplyKeyboardHide.unapply))

  implicit val replyMarkupWrites: Writes[ReplyMarkup] = new Writes[ReplyMarkup] {
    def writes(o: ReplyMarkup): JsValue = o match {
      case x: ReplyKeyboardMarkup => replyKeyboardMarkupWrites.writes(x)
      case x: ReplyKeyboardHide => replyKeyboardHideWrites.writes(x)
    }
  }

  implicit def successResponseReads[T: Reads]: Reads[Response.Success[T]] =
    (__ \ "result").read[T].map(Response.Success.apply)

  implicit def errorResponseReads: Reads[Response.Error] = (
    (__ \ "error_code").read[Int] and
    (__ \ "description").read[String]
  )(Response.Error.apply _)

  implicit def responseReads[T: Reads]: Reads[Response[T]] = new Reads[Response[T]] {
    def reads(j: JsValue): JsResult[Response[T]] =
      (j \ "ok").validate[Boolean].flatMap { ok =>
        if (ok) successResponseReads[T].reads(j)
        else errorResponseReads.reads(j)
      }
  }

  implicit val userReads: Reads[User] = (
    (__ \ "id").read[UserId] and
    (__ \ "first_name").read[String] and
    (__ \ "last_name").readNullable[String] and
    (__ \ "username").readNullable[String]
  )(User.apply _)

  implicit val chatTypeReads: Reads[ChatType] =
    __.read[String]
      .map(ChatType.fromString.get)
      .collect(ValidationError("Invalid ChatType string")) {
        case Some(ct) => ct
      }

  implicit val chatReads: Reads[Chat] = (
    (__ \ "id").read[Long] and
    (__ \ "type").read[ChatType] and
    (__ \ "title").readNullable[String] and
    (__ \ "username").readNullable[String] and
    (__ \ "first_name").readNullable[String] and
    (__ \ "last_name").readNullable[String]
  )(Chat.apply _)

  implicit val messageReads: Reads[Message] = (
    (__ \ "message_id").read[MessageId] and
    (__ \ "from").readNullable[User] and
    (__ \ "date").read[Long] and
    (__ \ "chat").read[Chat] and
    (__ \ "text").readNullable[String]
  )(Message.apply _)
}

object JsonSupport extends JsonSupport
