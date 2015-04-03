name := """cards9-server"""

version := "0.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
    "org.scalatest" % "scalatest_2.11" % "2.2.+" % "test",
    "org.scalacheck" %% "scalacheck" % "1.12.+" % "test"
)

fork in run := true
