// Project Settings

name := """play2-microservice-seed"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

javaOptions in Test ++= Seq(
  "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=9998",
  "-Xms512M",
  "-Xmx1536M",
  "-Xss1M",
  "-XX:MaxPermSize=384M"
)

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

// Docker Settings
dockerBaseImage := "isuper/java-oracle:jre_latest"

maintainer in Docker := "Dayel Ostraco <dayel.ostraco@qonceptual.com>"

dockerExposedPorts := Seq(8080)

dockerEntrypoint := Seq("bin/play2-microservice-seed", "-Dconfig.resource=application.conf", "-Dhttp.port=8080", "-J-javaagent:lib/com.newrelic.agent.java.newrelic-agent-3.22.0.jar")

dockerCmd := Seq("")

// Filters

includeFilter in (Assets, LessKeys.less) := "*.less"

excludeFilter in (Assets, LessKeys.less) := "_*.less"

// Dependencies

resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
  // If you enable PlayEbean plugin you must remove these
  // JPA dependencies to avoid conflicts.
  javaJpa,
  cache,
  javaWs,
  filters,
  // Evolutions
  "com.typesafe.play"               % "play-jdbc-evolutions_2.11" % "2.4.3",
  // Database Connectors
  "mysql"                           % "mysql-connector-java"      % "5.1.36",
  // JSON Parsing Engines
  "com.fasterxml.jackson.datatype"  % "jackson-datatype-jsr310"   % "2.4.4",
  "com.google.code.gson"            % "gson"                      % "2.2.4",
  // Utilities
  "org.jasypt"                      % "jasypt"                    % "1.9.1",
  "org.apache.commons"              % "commons-lang3"             % "3.4",
  // AWS Libraries
  "net.java.dev.jets3t"             % "jets3t"                    % "0.9.2",
  "com.amazonaws"                   % "aws-java-sdk"              % "1.6.1",
  // Notification Libraries
  "com.twilio.sdk"                  % "twilio-java-sdk"           % "3.4.6",
  "com.notnoop.apns"                % "apns"                      % "0.1.6",
  // Image Libraries
  "org.imgscalr"                    % "imgscalr-lib"              % "4.2",
  "net.coobird"                     % "thumbnailator"             % "0.4.8",
  // Object Mapping Libraries
  "ma.glasnost.orika"               % "orika-core"                % "1.4.5",
  // Payment Processing Libraries
  "com.stripe"                      % "stripe-java"               % "1.34.0",
  // Documentation Libraries
  "pl.matisoft"                     %% "swagger-play24"           % "1.4",
  // Swagger
  "com.qonceptual"                  %% "swagger-play2"            % "1.3.12_play24",
  // New Relic
  "com.newrelic.agent.java"         % "newrelic-agent"            % "3.22.0",
  // Test Dependencies
  "org.assertj"                     % "assertj-core"              % "3.1.0"             % "test"
)

// Routes Settings

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
