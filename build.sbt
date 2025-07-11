lazy val root = (project in file("."))
  .enablePlugins(GatlingPlugin)
  .enablePlugins(CorePlugin)
  .enablePlugins(JvmPlugin)
  .enablePlugins(IvyPlugin)
  .settings(
    organization := "uk.gov.hmrc",
    name := "address-lookup-performance-tests",
    version := "0.1.0",
    scalaVersion := "2.13.16",
    scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-language:postfixOps"),
    retrieveManaged := true,
    console / initialCommands := "import uk.gov.hmrc._",
    Test / parallelExecution := false,
    Test / publishArtifact := true,
    libraryDependencies ++= Dependencies.test,
    // Enabling sbt-auto-build plugin provides DefaultBuildSettings with default `testOptions` from `sbt-settings` plugin.
    // These testOptions are not compatible with `sbt gatling:test`. So we have to override testOptions here.
    Test / testOptions := Seq.empty
  )
