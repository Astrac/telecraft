package astrac.telecraft

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{FormData, HttpMethods, HttpRequest}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import akka.stream.scaladsl.Flow
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport._
import model._
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.{Json, Reads}

class TelegramChat(config: Config)(implicit as: ActorSystem, ec: ExecutionContext, mat: Materializer) {
  import JsonSupport._

  lazy val baseUri = s"https://api.telegram.org/${config.botToken}"
  lazy val http = Http()

  def request(uri: String, data: Map[String, String]): HttpRequest = HttpRequest(
    uri = s"$baseUri/$uri",
    method = HttpMethods.POST,
    entity = FormData(data).toEntity
  )

  def requestFor(cmd: Command[_]): HttpRequest = cmd match {
    case GetMe =>
      request("getMe", Map.empty)

    case SendMessage(chatId, text, parseMode, disableWebPagePreview, replyToMessageId, replyMarkup) =>
      request("sendMessage", Map(
        "chat_id" -> Option(chatId.toString),
        "text" -> Option(text),
        "parse_mode" -> parseMode,
        "reply_to_message_id" -> replyToMessageId.map(_.toString),
        "reply_markup" -> replyMarkup.map(m => Json.stringify(Json.toJson(m)))
      ).flatMap { case (k, v) => v.map((k, _)) })
  }

  def send[R: Reads](cmd: Command[R]): Future[Response[R]] = {
    http
      .singleRequest(requestFor(cmd))
      .flatMap(response => Unmarshal(response.entity).to[Response[R]])
  }
}
