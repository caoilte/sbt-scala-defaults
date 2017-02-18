package org.caoilte.sbt

import sbt._
import sbt.Keys._

object V {
  val scalafmt = "0.5.5"
}

object Libraries {
  val scalafmtBootstrap = "com.geirsson" %% "scalafmt-bootstrap" % V.scalafmt
  val fansi = "com.lihaoyi" %% "fansi" % "0.2.3"

  val all = Seq(scalafmtBootstrap, fansi)
}

object Plugins {
  val sbtImportScalaFiles = "org.caoilte" % "sbt-import-scala-files" % "1.0.0"
  val sbtJavaVersionCheck = "com.typesafe.sbt" % "sbt-javaversioncheck" % "0.1.0"

  val all = Seq(sbtImportScalaFiles, sbtJavaVersionCheck)
}

object Dependencies {
  val allLibrariesAsDependenciesSetting = libraryDependencies ++= Libraries.all
  val allAddPluginSettings = Plugins.all.map(addSbtPlugin)

  val allLibrariesAndAddPluginSettings: Seq[Setting[_]] =
    Seq(allLibrariesAsDependenciesSetting) ++ allAddPluginSettings
}
