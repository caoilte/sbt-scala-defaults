package org.caoilte.sbt

import sbt._
import sbt.Keys._

object V {
  val scalafmt = "0.6.8"
  val acyclic  = "0.1.7"
}

object Libraries {
  val fansi   = "com.lihaoyi" %% "fansi"   % "0.2.3"
  val acyclic = "com.lihaoyi" %% "acyclic" % V.acyclic % "provided"

  val all = Seq(fansi, acyclic)
}

object SBTPlugins {
  val sbtImportScalaFiles = "org.caoilte"             % "sbt-import-scala-files" % "1.0.0"
  val sbtScalafmt         = "com.geirsson"            % "sbt-scalafmt"           % V.scalafmt
  val coursier            = "io.get-coursier"         % "sbt-coursier"           % "1.0.0-M15-5"
  val clippy              = "com.softwaremill.clippy" % "plugin-sbt"             % "0.5.2"
  val sbtRelease          = "com.github.gseitz"       % "sbt-release"            % "1.0.4"

  // Not included in all, as doesn't really work most of the time.
  val sbtJavaVersionCheck = "com.typesafe.sbt" % "sbt-javaversioncheck" % "0.1.0"

  // Only needed for opensource plugins
  val sonatype              = "org.xerial.sbt" % "sbt-sonatype" % "1.1"
  val pgp                   = "com.jsuereth" % "sbt-pgp" % "1.0.0"
  val forOpensourceProjects = Seq(sonatype, pgp, sbtRelease)

  val base = Seq(sbtImportScalaFiles, sbtScalafmt, coursier, clippy)
}

object CompilerPlugins {
  val acyclic = "com.lihaoyi" %% "acyclic" % "0.1.7"

  val all = Seq(acyclic)
}

object Dependencies {
  val allLibrariesAsDependenciesSetting = libraryDependencies ++= Libraries.all
  val baseSBTPluginsAsSettings          = SBTPlugins.base.map(addSbtPlugin)
  val allCompilerPluginsAsSettings      = CompilerPlugins.all.map(addCompilerPlugin)

  val baseLibrariesAndPluginsAsSettings: Seq[Setting[_]] =
    Seq(allLibrariesAsDependenciesSetting) ++ baseSBTPluginsAsSettings ++ allCompilerPluginsAsSettings
}
