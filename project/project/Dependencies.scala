package org.caoilte.sbt

import sbt._
import sbt.Keys._

object V {
  val scalafmt = "0.6.2"
  val acyclic  = "0.1.7"
}

object Libraries {
  val fansi   = "com.lihaoyi" %% "fansi"   % "0.2.3"
  val acyclic = "com.lihaoyi" %% "acyclic" % V.acyclic % "provided"

  val all = Seq(fansi, acyclic)
}

object SBTPlugins {
  val sbtImportScalaFiles = "org.caoilte"  % "sbt-import-scala-files" % "1.0.0"
  val sbtScalafmt         = "com.geirsson" % "sbt-scalafmt"           % V.scalafmt

  // Not included in all, as doesn't really work most of the time.
  val sbtJavaVersionCheck = "com.typesafe.sbt" % "sbt-javaversioncheck" % "0.1.0"

  val all = Seq(sbtImportScalaFiles, sbtScalafmt)
}

object CompilerPlugins {
  val acyclic = "com.lihaoyi" %% "acyclic" % "0.1.7"

  val all = Seq(acyclic)
}

object Dependencies {
  val allLibrariesAsDependenciesSetting = libraryDependencies ++= Libraries.all
  val allSBTPluginsAsSettings           = SBTPlugins.all.map(addSbtPlugin)
  val allCompilerPluginsAsSettings      = CompilerPlugins.all.map(addCompilerPlugin)

  val allLibrariesAndPluginsAsSettings: Seq[Setting[_]] =
    Seq(allLibrariesAsDependenciesSetting) ++ allSBTPluginsAsSettings ++ allCompilerPluginsAsSettings
}
