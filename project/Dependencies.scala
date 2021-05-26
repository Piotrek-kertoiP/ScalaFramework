import sbt._

object Dependencies {

  private val akkaVsn = "2.6.8"
  private val akkaHttpVsn = "10.2.0"
  private val circeVsn = "0.13.0"
  private val enumeratumVsn = "1.5.15"
  private val jsonVsn = "3.6.7"
  private val sttpVersion = "1.1.6"

  val all: Seq[ModuleID] = Seq(
    "io.circe"                     %% "circe-core"                        % circeVsn,
    "io.circe"                     %% "circe-generic"                     % circeVsn,
    "io.circe"                     %% "circe-parser"                      % circeVsn,
    "com.beachape"                 %% "enumeratum"                        % "1.6.1",
    "de.heikoseeberger"            %% "akka-http-circe"                   % "1.34.0",
    "com.typesafe"                  % "config"                            % "1.4.0",
    "com.typesafe.akka"            %% "akka-actor"                        % akkaVsn,
    "com.typesafe.akka"            %% "akka-actor-typed"                  % akkaVsn,
    "com.typesafe.akka"            %% "akka-slf4j"                        % akkaVsn,
    "com.typesafe.akka"            %% "akka-stream"                       % akkaVsn,
    "com.typesafe.akka"            %% "akka-stream-typed"                 % akkaVsn,
    "com.typesafe.akka"            %% "akka-http"                         % akkaHttpVsn,
    "com.typesafe.akka"            %% "akka-http-core"                    % akkaHttpVsn,
    "com.github.pathikrit"         %% "better-files"                      % "3.9.1",
    "com.typesafe.scala-logging"   %% "scala-logging"                     % "3.9.2",
    "net.logstash.logback"          % "logstash-logback-encoder"          % "4.8",
    "com.fasterxml.jackson.core"    % "jackson-databind"                  % "2.11.2",
    "org.codehaus.janino"           % "janino"                            % "3.1.2",
    "org.slf4j"                     % "slf4j-api"                         % "1.7.30",
    "io.kamon"                     %% "kamon-bundle"                      % "2.1.4",
    "io.kamon"                     %% "kamon-prometheus"                  % "2.1.4",
    "io.circe"                     %% "circe-core"                        % circeVsn,
    "io.circe"                     %% "circe-generic"                     % circeVsn,
    "io.circe"                     %% "circe-parser"                      % circeVsn,
    "de.heikoseeberger"            %% "akka-http-circe"                   % "1.32.0",
    "de.heikoseeberger"            %% "akka-http-json4s"                  % "1.29.1",
    "org.json4s"                   %% "json4s-native"                     % jsonVsn,
    "org.json4s"                   %% "json4s-ext"                        % jsonVsn,
    "com.beachape"                 %% "enumeratum-json4s"                 % enumeratumVsn,
    "com.softwaremill.sttp"        %% "core"                              % sttpVersion,
    "com.softwaremill.sttp"        %% "async-http-client-backend-cats"    % sttpVersion,
    "com.softwaremill.common"      %% "tagging"                           % "2.3.0",
    "org.apache.tika"               % "tika-core"                         % "1.26",
    "org.tpolecat"                 %% "doobie-core"                       % "0.8.6",
    "org.tpolecat"                 %% "doobie-hikari"                     % "0.8.6",
    "org.tpolecat"                 %% "doobie-postgres"                   % "0.8.6",
    "org.flywaydb"                  % "flyway-core"                       % "4.2.0"
  )

}
