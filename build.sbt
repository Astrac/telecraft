resolvers ++= Seq(
  "SpigotMC Nexus" at "https://hub.spigotmc.org/nexus/content/repositories/snapshots/",
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

scalaVersion := "2.11.7"

val akkaV = "2.4.1"
val akkaHttpV = "2.0.3"
val spigotApiV = "1.8.8-R0.1-SNAPSHOT"

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.2.5",
  "com.typesafe.akka" %% "akka-actor" % akkaV,
  "com.typesafe.akka" %% "akka-http-core-experimental" % akkaHttpV,
  "com.typesafe.akka" %% "akka-http-experimental" % akkaHttpV,
  "com.typesafe.akka" %% "akka-stream-experimental" % akkaHttpV,
  "com.typesafe.akka" %% "akka-slf4j" % akkaV,
  "com.typesafe.play" %% "play-json" % "2.4.6",
  "de.heikoseeberger" %% "akka-http-play-json" % "1.4.2",
  "org.bukkit" % "bukkit" % spigotApiV % "provided",
  "org.spigotmc" % "spigot-api" % spigotApiV % "provided"
)
