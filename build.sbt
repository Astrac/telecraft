resolvers += "SpigotMC Nexus" at "https://hub.spigotmc.org/nexus/content/repositories/snapshots/"
resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies ++= Seq(
  "org.spigotmc" % "spigot-api" % "1.8.8-R0.1-SNAPSHOT" % "provided",
  "org.bukkit" % "bukkit" % "1.8.8-R0.1-SNAPSHOT" % "provided"
)
