package astrac.telecraft

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import com.typesafe.config.ConfigFactory
import java.io.{File, InputStreamReader}
import java.util.logging.Logger
import org.bukkit.plugin.java.JavaPlugin
import scala.concurrent.ExecutionContext

class TelecraftPlugin extends JavaPlugin {

  // Work-around for spigot classpath meddling
  val refConfig = ConfigFactory.parseReader(new InputStreamReader(getResource("reference.conf")))
  val appConfig = ConfigFactory.load().withFallback(refConfig).resolve()

  // Work-around for spigot classloader conflict with akka
  getClassLoader.loadClass("akka.event.DefaultLoggingFilter")

  implicit lazy val ec = ExecutionContext.global
  implicit lazy val as = ActorSystem("telecraft", appConfig, getClassLoader)
  implicit lazy val mat = ActorMaterializer()

  val logger = getLogger
  val config = {
    val fileConfig = getConfig
    fileConfig.load(new File(getDataFolder, "config.yml"))
    Config.fromSpigot(fileConfig)
  }

  private def setup(conf: Config): Unit = {
    val chat = new TelegramChat(conf)
    val playerListener = new PlayerJoinListener(chat, conf.chatId, logger)
    getServer.getPluginManager.registerEvents(playerListener, this)
  }

  override def onEnable(): Unit = {
    config.fold({
      getLogger.warning("Invalid configuration provided for telecraft")
      setEnabled(false)
    })(setup)
  }
}
