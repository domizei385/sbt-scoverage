name := "sbt-scoverage"

organization := "org.scoverage"

enablePlugins(SbtPlugin)

scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8")

resolvers ++= {
  if (isSnapshot.value) Seq(Resolver.sonatypeRepo("snapshots")) else Nil
}

libraryDependencies += "org.scoverage" %% "scalac-scoverage-plugin" % "1.4.2"

publishMavenStyle := true

Test / publishArtifact := false

scriptedLaunchOpts ++= Seq(
  "-Xmx1024M",
  "-Dplugin.version=" + version.value
)

import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._

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
  //val repo = "https://maven.zalando.net/repository/"
  val repo = "http://192.168.178.127:8082/artifactory/"
  if (isSnapshot.value) Some(Resolver.url("snapshots", url(repo + "snapshots")).withAllowInsecureProtocol(true))
  else Some(Resolver.url("releases", url(repo + "releases")).withAllowInsecureProtocol(true))
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

sbtVersion := "1.5.0"
scalaVersion := "2.12.13"
crossScalaVersions := Seq(scalaVersion.value, "2.13.5")

scalariformAutoformat := false
