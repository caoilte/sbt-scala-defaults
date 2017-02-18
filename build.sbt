lazy val commonSettings = Seq(
  sbtPlugin := true,
  organization := "org.caoilte",
  description := "SBT Plugin to provide sensible defaults for a Scala project",
  licenses := Seq(
    "Apache 2.0 License" -> url(
      "https://gitlab.com/caoilte/sbt-scala-defaults/raw/master/LICENSE"
    )
  ),
  filesToImport ++= Seq(file("project/project/Library.scala"))
)

lazy val root = (project in file("."))
  .settings(
    Seq(publishArtifact := false)
  )
  .aggregate(
    `universal-defaults`,
    `scala-2-10-defaults`,
    `scala-2-11-defaults`,
    `scala-2-12-defaults`,
    `scalafmt-defaults`,
    `plugin-defaults`,
    `project-defaults`
  )

lazy val `universal-defaults` =
  project.settings(
    commonSettings++
      Seq(
        filesToImport ++= Seq(
          file("project/UniversalDefaultsPlugin.scala")
        )
      )
  )

lazy val `scala-2-10-defaults` =
  project.settings(
    commonSettings ++
      Seq(
        filesToImport ++= Seq(
          file("project/Scala210SettingsPlugin.scala")
      )
    )
  )
lazy val `scala-2-11-defaults` = project.settings(commonSettings: _*)
lazy val `scala-2-12-defaults` = project.settings(commonSettings: _*)

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

lazy val `plugin-defaults` = project
  .settings(commonSettings: _*)
  .dependsOn(
    `scalafmt-defaults`,
    `scala-2-10-defaults`,
    `universal-defaults`
  )

lazy val `project-defaults` = project
  .settings(commonSettings: _*)
  .dependsOn(
    `scalafmt-defaults`,
    `scala-2-12-defaults`,
    `universal-defaults`
  )