organization := "piotrek-kertoip"
name := "sandbox"
version := IO.read(file("src/main/resources/version")).stripLineEnd

scalaVersion := "2.12.10"

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "utf8",
  "-explaintypes",
  "-feature",
  "-language:higherKinds,implicitConversions",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint:_,-missing-interpolator",
  "-Yrangepos",
  "-Ywarn-dead-code",
  "-Ywarn-extra-implicit",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused:_"
)
Compile / console / scalacOptions --= Seq("-Ywarn-unused:_", "-Xfatal-warnings")

libraryDependencies ++= Dependencies.all
// Logging bridges - 3rd party modules use loggers other than slf4j
excludeDependencies ++= Seq(
  "commons-logging"           % "commons-logging",
  "org.apache.logging.log4j"  % "log4j-api-scala",
  "org.apache.logging.log4j"  % "log4j-api",
  "org.apache.logging.log4j"  % "log4j-core"
)
libraryDependencies ++= Seq(
  "org.slf4j"                 % "log4j-over-slf4j"  % "1.7.30",
  "org.slf4j"                 % "jcl-over-slf4j"    % "1.7.30",
  "org.slf4j"                 % "jul-to-slf4j"      % "1.7.30"
)

// Wartremover
Compile / compile / wartremoverErrors ++= Wartremover.defaultWarts
// exclude for protobuf
wartremoverExcluded ++= (Compile / managedSourceDirectories).value

// Scalastyle
scalastyleConfig := file("project/scalastyle-config.xml")