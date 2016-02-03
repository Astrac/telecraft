package astrac.telecraft

import JsonSupport._
import akka.stream.Materializer
import java.util.logging.{Level, Logger}
import model.DataId._
import model.SendMessage
import org.bukkit.event.{EventHandler, Listener}
import org.bukkit.event.player.PlayerJoinEvent
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class PlayerJoinListener(
    chat: TelegramChat,
    chatId: ChatId,
    logger: Logger
)(implicit ec: ExecutionContext) extends Listener {

  @EventHandler
  def onPlayerJoin(event: PlayerJoinEvent): Unit = {
    val cmd = SendMessage(chatId, s"${event.getPlayer.getName} has just logged in")

    logger.log(Level.INFO, s"Player joined: $event.getPlayer.getName - sending cmd: $cmd")

    chat.send(cmd).onComplete {
      case Success(messageSent) => logger.log(Level.INFO, s"Sent joinMessage for user ${event.getPlayer.getName} - $messageSent")
      case Failure(ex) => logger.log(Level.WARNING, s"Cannot send message to telegram chat: ${ex.getMessage}", ex)
    }
  }
}
