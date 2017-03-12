import org.caoilte.sbt._

lazy val commonSettings = Seq(
  sbtPlugin := true,
  organization := "org.caoilte",
  description := "SBT Plugin to provide sensible defaults for a Scala project",
  licenses := Seq(
    "Apache 2.0 License" -> url(
      "https://gitlab.com/caoilte/sbt-scala-defaults/raw/master/LICENSE"
    )
  ),
  filesToImport ++= Seq(file("project/project/Dependencies.scala")),
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
    `scala-2-10-defaults`,
    `scala-2-11-defaults`,
    `scala-2-12-defaults`,
    `scalafmt-defaults`,
    `plugin-defaults`,
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

lazy val `scala-2-10-defaults` =
  project
    .settings(
      commonSettings ++
        Seq(
          filesToImport ++= Seq(file("project/Scala210DefaultsPlugin.scala"))
        )
    )
    .dependsOn(`universal-defaults`)

lazy val `scala-2-11-defaults` =
  project.settings(commonSettings: _*).dependsOn(`universal-defaults`)

lazy val `scala-2-12-defaults` =
  project
    .settings(
      commonSettings ++
        Seq(
          addSbtPlugin(SBTPlugins.sbtJavaVersionCheck)
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

lazy val `plugin-defaults` = project
  .settings(commonSettings: _*)
  .dependsOn(`scalafmt-defaults`, `scala-2-10-defaults`)

lazy val `project-defaults` = project
  .settings(commonSettings: _*)
  .dependsOn(`scalafmt-defaults`, `scala-2-12-defaults`)
