name := """cards9-server"""

version := "0.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies ++= Seq(
    "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
    "org.scalacheck" %% "scalacheck" % "1.12.4" % "test",
    "com.beachape" %% "enumeratum" % "1.2.2"
)

fork in run := true
