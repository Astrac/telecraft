package astrac.telecraft

import org.bukkit.configuration.file.FileConfiguration

import model.DataId._

case class Config(
  botToken: String,
  chatId: ChatId
)

object Config {
  def fromSpigot(spigotConfig: FileConfiguration): Option[Config] =
    for {
      tkn <- Option(spigotConfig.getString("telecraft.bot_token"))
      cid <- Option(spigotConfig.getLong("telecraft.chat_id")).map(shapeless.tag[ChatIdType](_))
    } yield Config(tkn, cid)
}
