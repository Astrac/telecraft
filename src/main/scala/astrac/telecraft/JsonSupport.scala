package astrac.telecraft

import io.circe.{Encoder, Decoder}
import shapeless.tag
import tag.@@

trait JsonSupport {
  implicit def taggedLongEncoder[T]: Encoder[Long @@ T] = Encoder[Long].contramap(identity)
  implicit def taggedLongDecoder[T]: Decoder[Long @@ T] = Decoder[Long].map(tag[T](_))
}

object JsonSupport extends JsonSupport
