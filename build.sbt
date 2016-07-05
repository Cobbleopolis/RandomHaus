name := "RandomHaus"


version := "2.4.0"


lazy val `randomhaus` = (project in file(".")).enablePlugins(PlayScala, DebianPlugin, BuildInfoPlugin)

scalaVersion := "2.11.8"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies ++= Seq(
	jdbc,
	cache,
	ws,
	"org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test",
	"org.pegdown" % "pegdown" % "1.4.2",
	"com.google.api-client" % "google-api-client" % "1.20.0",
	"com.google.api-client" % "google-api-client-java6" % "1.20.0",
	"com.google.api-client" % "google-api-client-jackson2" % "1.20.0",
	"com.google.oauth-client" % "google-oauth-client-jetty" % "1.11.0-beta",
	"com.google.apis" % "google-api-services-youtube" % "v3-rev166-1.21.0",
	"com.typesafe.play" %% "anorm" % "3.0.0-M1",
	"org.webjars" %% "webjars-play" % "2.5.0",
	"mysql" % "mysql-connector-java" % "5.1.36",
	"com.adrianhurt" %% "play-bootstrap" % "1.0-P25-B3" exclude("org.webjars", "jquery"),
	"org.webjars" % "bootstrap-sass" % "3.3.1-1",
	"org.webjars" % "jquery" % "2.2.2",
	"org.webjars.bower" % "lodash" % "4.11.1",
	"org.webjars.bower" % "font-awesome-sass" % "4.6.2"
)

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolveFromWebjarsNodeModulesDir := true

typingsFile := Some(baseDirectory.value / "app" / "assets" / "javascripts" / "typings" / "tsd.d.ts")

maintainer in Linux := "Logan Thompson <cobbleopolis@gmail.com>"

packageSummary in Linux := "RandomHaus server"

packageDescription := "A play server to run a RandomHaus instance"

//javaOptions in Test += "-Dconfig.file=conf/application.test.conf"

(testOptions in Test) += Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/report")

bashScriptExtraDefines += """addJava "-Dconfig.file=${app_home}/../conf/production.conf""""

val ignoredFiles: Seq[String] = Seq(
    "client.json",
    "client.json.enc"
)

mappings in (Compile, packageBin) ~= { _.filterNot { case (_, s) =>
    ignoredFiles.contains(s)
}}