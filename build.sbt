name := """cards9-server"""

version := "0.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

routesGenerator := InjectedRoutesGenerator

libraryDependencies ++= Seq(
    "com.typesafe.play" %% "play-slick" % "1.0.0",
    "com.typesafe.play" %% "play-slick-evolutions" % "1.0.0",
    "org.postgresql" % "postgresql" % "9.3-1103-jdbc41",
    "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
    "org.scalacheck" %% "scalacheck" % "1.12.4" % "test",
    "com.beachape" %% "enumeratum" % "1.2.2"
)

fork in run := true
