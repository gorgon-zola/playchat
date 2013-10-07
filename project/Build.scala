import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "websochat"
    val appVersion      = "1.0"

    val appDependencies = Seq(
      // Add your project dependencies here,
      "mysql" % "mysql-connector-java" % "5.1.21", jdbc, anorm,
      "org.pac4j" % "play-pac4j_scala2.10" % "1.1.1",
      "org.pac4j" % "pac4j-http" % "1.4.2-SNAPSHOT",
      "org.pac4j" % "pac4j-cas" % "1.4.2-SNAPSHOT",
      "org.pac4j" % "pac4j-openid" % "1.4.2-SNAPSHOT",
      "org.pac4j" % "pac4j-oauth" % "1.4.2-SNAPSHOT"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
      // Add your own project settings here      
      testOptions in Test += Tests.Argument("junitxml", "console"),
      resolvers += "Sonatype snapshots repository" at "https://oss.sonatype.org/content/repositories/snapshots/"
    )
}
