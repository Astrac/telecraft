package astrac.telecraft

import JsonSupport._
import akka.stream.Materializer
import java.util.logging.{Level, Logger}
import io.circe.generic.auto._
import model.DataId._
import model.{Message, Response, SendMessage}
import org.bukkit.event.{EventHandler, Listener}
import org.bukkit.event.player.{PlayerJoinEvent, PlayerQuitEvent, PlayerEvent}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class PlayerJoinListener(
    chat: TelegramChat,
    chatId: ChatId,
    logger: Logger
)(implicit ec: ExecutionContext) extends Listener {

  def execute(cmd: SendMessage): Unit = chat.send(cmd).onComplete {
    case Success(messageSent) => logger.log(Level.INFO, s"Sent message to chat ${chatId}: ${messageSent}")
    case Failure(ex) => logger.log(Level.WARNING, s"Cannot send message to telegram chat: ${ex.getMessage}", ex)
  }

  @EventHandler
  def onPlayerJoin(event: PlayerJoinEvent): Unit = {
    execute(SendMessage(chatId, s"${event.getPlayer.getName} has just logged in"))
  }

  @EventHandler
  def onPlayerQuit(event: PlayerQuitEvent): Unit = {
    execute(SendMessage(chatId, s"${event.getPlayer.getName} has just logged out"))
  }
}
