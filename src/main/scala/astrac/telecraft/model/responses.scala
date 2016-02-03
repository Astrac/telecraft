package astrac.telecraft.model

sealed trait Response[+T]

object Response {
  case class Error(errorCode: Int, description: String) extends Response[Nothing]

  case class Success[T](result: T) extends Response[T]
}
