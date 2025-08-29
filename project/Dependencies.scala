import sbt.*

object Dependencies {

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"           %% "performance-test-runner"  % "6.2.0",
    "org.playframework"     %% "play-json"                % "3.0.5"
  ).map(_ % Test)
}
