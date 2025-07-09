import sbt._

object Dependencies {

  val test = Seq(
    "uk.gov.hmrc"           %% "performance-test-runner"  % "6.1.0"   % Test,
    "com.typesafe.play"     %% "play-json"                % "2.10.7"  % Test
  )
}
