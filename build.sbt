name := "RandomHaus"

version := "1.0"

lazy val `randomhaus` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies ++= Seq(
	jdbc,
	cache,
	ws,
	specs2 % Test,
	"com.google.api-client" % "google-api-client" % "1.20.0",
	"com.google.api-client" % "google-api-client-java6" % "1.20.0",
	"com.google.api-client" % "google-api-client-jackson2" % "1.20.0",
	"com.google.oauth-client" % "google-oauth-client-jetty" % "1.11.0-beta",
	"com.google.apis" % "google-api-services-youtube" % "v3-rev166-1.21.0",
	"com.typesafe.play" %% "anorm" % "3.0.0-M1",
	"org.webjars" %% "webjars-play" % "2.5.0",
	"mysql" % "mysql-connector-java" % "5.1.18",
	"com.adrianhurt" %% "play-bootstrap" % "1.0-P25-B3" exclude("org.webjars", "jquery"),
	"org.webjars" % "bootstrap-sass" % "3.3.1-1",
	"org.webjars" % "jquery" % "2.2.2"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"  