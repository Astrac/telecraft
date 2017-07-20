package astrac.telecraft

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{FormData, HttpMethods, HttpRequest, RequestEntity}
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import akka.stream.scaladsl.Flow
import de.heikoseeberger.akkahttpcirce.CirceSupport._
import io.circe.{Encoder, Decoder}
import io.circe.generic.auto._
import model._
import scala.concurrent.{ExecutionContext, Future}

class TelegramChat(config: Config)(implicit as: ActorSystem, ec: ExecutionContext, mat: Materializer) {
  import JsonSupport._

  lazy val baseUri = s"https://api.telegram.org/${config.botToken}"
  lazy val http = Http()

  def send[C, R](cm: C)(implicit cmd: Cmd[C, R], w: Encoder[C], r: Decoder[R]): Future[Response[R]] =
    for {
      entity <- Marshal(cm).to[RequestEntity]
      request = HttpRequest(
        uri = s"$baseUri/${cmd.uri}",
        method = HttpMethods.POST,
        entity = entity
      )
      httpResp <- http.singleRequest(request)
      response <- Unmarshal(httpResp.entity).to[Response[R]]
    } yield response
}
