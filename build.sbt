val appName = "address-lookup-performance-tests"
val appVersion = "0.1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(GatlingPlugin)
  .enablePlugins(CorePlugin)
  .enablePlugins(JvmPlugin)
  .enablePlugins(IvyPlugin)
  .enablePlugins(SbtAutoBuildPlugin)
  .settings(
    organization := "uk.gov.hmrc",
    name := appName,
    version := appVersion,
    scalaVersion := "2.12.12",
    scalacOptions ++= Seq("-feature"),
    retrieveManaged := true,
    initialCommands in console := "import uk.gov.hmrc._",
    parallelExecution in Test := false,
    publishArtifact in Test := true,
    libraryDependencies ++= Dependencies.test,
    // Enabling sbt-auto-build plugin provides DefaultBuildSettings with default `testOptions` from `sbt-settings` plugin.
    // These testOptions are not compatible with `sbt gatling:test`. So we have to override testOptions here.
    testOptions in Test := Seq.empty,
    resolvers ++= Seq(
      Resolver.bintrayRepo("hmrc", "releases"),
      Resolver.typesafeRepo("releases")
    )
  )