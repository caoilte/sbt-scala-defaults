lazy val commonSettings = Seq(
  sbtPlugin := true,
  organization := "org.caoilte",
  scalaVersion := "2.10.6",
  description := "SBT Plugin to provide sensible defaults for a Scala project",
  licenses := Seq(
    "Apache 2.0 License" -> url(
      "https://gitlab.com/caoilte/sbt-scala-defaults/raw/master/LICENSE"
    )
  ),
  publishArtifact in (Compile, packageBin) := true,
  publishArtifact in (Test, packageBin) := false,
  publishArtifact in (Compile, packageDoc) := false,
  publishArtifact in (Compile, packageSrc) := true,
  filesToImport ++= Seq(file("project/project/Library.scala"))
)

lazy val root = (project in file("."))
  .settings(
    Seq(publishArtifact := false)
  )
  .aggregate(
    `build-utils`,
    `scala-2-11-defaults`,
    `scalafmt-defaults`,
    `sensible-defaults`
  )

lazy val `build-utils` = project.settings(commonSettings: _*)

lazy val `scala-2-11-defaults` = project.settings(commonSettings: _*)

lazy val `scalafmt-defaults` = project
  .settings(
    commonSettings ++
      Seq(
        libraryDependencies ++= Seq(
          common.Library.scalafmtBootstrap,
          common.Library.fansi
        ),
        filesToImport ++= Seq(
          file("project/SetupScalafmtPlugin.scala"),
          file("project/IncrementalScalafmtPlugin.scala")
        )
      )
  )
  .dependsOn(`build-utils`)

lazy val `sensible-defaults` = project
  .settings(commonSettings: _*)
  .dependsOn(`scalafmt-defaults`, `scala-2-11-defaults`, `build-utils`)
