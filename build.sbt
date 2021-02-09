name := "sbt-scoverage"

organization := "org.scoverage"

enablePlugins(SbtPlugin)

scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8")

resolvers ++= {
  if (isSnapshot.value) Seq(Resolver.sonatypeRepo("snapshots")) else Nil
}

libraryDependencies += "org.scoverage" %% "scalac-scoverage-plugin" % "1.4.2"

publishMavenStyle := true

publishArtifact in Test := false

scriptedLaunchOpts ++= Seq(
  "-Xmx1024M",
  "-Dplugin.version=" + version.value
)

import ReleaseTransformations._
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  releaseStepCommandAndRemaining("^ test"),
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommandAndRemaining("^ publish"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)

releaseCrossBuild := false

publishTo := {
//  val repo = "https://maven.zalando.net/"
  val repo = "http://localhost:8082/"
  if (isSnapshot.value) {
//    Some("snapshots" at repo + "content/repositories/snapshots")
    Some("releases" at repo + "artifactory/snapshots")
  }
  else {
    //    Some("releases" at repo + "content/repositories/releases")
        Some("releases" at repo + "artifactory/releases")
  }
}

pomExtra := {
  <url>https://github.com/scoverage/sbt-scoverage</url>
    <licenses>
      <license>
        <name>Apache 2</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:scoverage/sbt-scoverage.git</url>
      <connection>scm:git@github.com:scoverage/sbt-scoverage.git</connection>
    </scm>
    <developers>
      <developer>
        <id>sksamuel</id>
        <name>sksamuel</name>
        <url>http://github.com/sksamuel</url>
      </developer>
      <developer>
        <id>gslowikowski</id>
        <name>Grzegorz Slowikowski</name>
        <url>http://github.com/gslowikowski</url>
      </developer>
    </developers>
}

crossSbtVersions := Vector("1.2.8", "1.4.7")

scalariformAutoformat := false
