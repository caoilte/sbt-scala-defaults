import org.caoilte.sbt._

lazy val commonSettings = Seq(
  sbtPlugin := true,
  organization := "org.caoilte",
  description := "SBT Plugin to provide sensible defaults for a Scala project",
  filesToImport ++= Seq(file("project/project/Dependencies.scala")),
  scalaVersion := "2.10.6"
)

lazy val root = (project in file("."))
  .settings(
    Seq(
      publishArtifact := false,
      organization := "org.caoilte",
      name := "sbt-scala-defaults"
    )
  )
  .aggregate(
    `universal-defaults`,
    `scalac-defaults`,
    `scalafmt-defaults`,
    `project-defaults`
  )

lazy val `universal-defaults` =
  project.settings(
    commonSettings ++
      Seq(
        addSbtPlugin(SBTPlugins.sbtImportScalaFiles),
        addSbtPlugin(SBTPlugins.coursier),
        addSbtPlugin(SBTPlugins.clippy),
        clippyColorsEnabled := true,
        filesToImport ++= Seq(file("project/UniversalDefaultsPlugin.scala"))
      )
  )

lazy val `scalac-defaults` =
  project
    .settings(
      commonSettings ++
        Seq(
          filesToImport ++= Seq(file("project/ScalacDefaultsPlugin.scala"))
        )
    )
    .dependsOn(`universal-defaults`)

lazy val `scalafmt-defaults` = project
  .settings(
    commonSettings ++
      Seq(
        libraryDependencies ++= Seq(
          Libraries.fansi
        ),
        filesToImport ++= Seq(
          file("project/ScalafmtDefaultsPlugin.scala")
        ),
        addSbtPlugin(SBTPlugins.sbtScalafmt)
      )
  )

lazy val `project-defaults` = project
  .settings(commonSettings: _*)
  .dependsOn(`scalafmt-defaults`, `scalac-defaults`)

pomExtra in Global := {
  <url>https://github.com/caoilte/sbt-scala-defaults/</url>
    <licenses>
      <license>
        <name>Apache 2</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
      </license>
    </licenses>
    <scm>
      <connection>scm:git:github.com/caoilte/sbt-scala-defaults.git</connection>
      <developerConnection>scm:git:git@github.com:caoilte/sbt-scala-defaults.git</developerConnection>
      <url>github.com/caoilte/sbt-scala-defaults/</url>
    </scm>
    <developers>
      <developer>
        <id>caoilte</id>
        <name>Caoilte O'Connor</name>
        <url>http://caoilte.org</url>
      </developer>
    </developers>
}

import ReleaseTransformations._

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  ReleaseStep(action = Command.process("publishSigned", _)),
  setNextVersion,
  commitNextVersion,
  ReleaseStep(action = Command.process("sonatypeReleaseAll", _)),
  pushChanges
)