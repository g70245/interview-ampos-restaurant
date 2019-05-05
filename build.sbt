name := "interview-ampos-mini"
organization := "com.ampos"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  javaJdbc,
  javaWs,
  guice,
  "mysql" % "mysql-connector-java" % "8.0.8-dmr",
  "org.flywaydb" %% "flyway-play" % "5.3.2"
)
