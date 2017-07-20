resolvers ++= Seq(
  "SpigotMC Nexus" at "https://hub.spigotmc.org/nexus/content/repositories/snapshots/",
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

scalaVersion := "2.12.2"

val spigotApiV = "1.12-R0.1-SNAPSHOT"
val circeV = "0.8.0"

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.2",
  "org.typelevel" %% "cats" % "0.9.0",
  "com.typesafe.akka" %% "akka-http" % "10.0.9",
  "io.circe" %% "circe-core" % circeV,
  "io.circe" %% "circe-generic" % circeV,
  "io.circe" %% "circe-parser" % circeV,
  "de.heikoseeberger" %% "akka-http-circe" % "1.17.0",
  "org.bukkit" % "bukkit" % spigotApiV % "provided",
  "org.spigotmc" % "spigot-api" % spigotApiV % "provided",
  "com.lihaoyi" % "ammonite" % "1.0.0" % "test" cross CrossVersion.full
)

sourceGenerators in Test += Def.task {
  val file = (sourceManaged in Test).value / "amm.scala"
  IO.write(file, """object amm extends App { ammonite.Main().run() }""")
  Seq(file)
}.taskValue
