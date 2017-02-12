package common

import sbt.Keys._
import sbt._

object V {
  val scalafmt = "0.5.5"
}

object Library {
  val scalafmtBootstrap = "com.geirsson" %% "scalafmt-bootstrap" % V.scalafmt
  val fansi = "com.lihaoyi" %% "fansi" % "0.2.3"
  val importScalaFiles = "org.caoilte" % "sbt-import-scala-files" % "1.0.0"

  val pluginDependencySeq = Seq(importScalaFiles)
  val libraryDependencySeq = Seq(scalafmtBootstrap, fansi)

  val libraryDependenciesAsSettings = libraryDependencies ++= libraryDependencySeq
  val pluginDependenciesAsSettings = pluginDependencySeq.map(addSbtPlugin)

  val dependenciesAsSettings: Seq[Setting[_]] = Seq(
      libraryDependenciesAsSettings
    ) ++ pluginDependenciesAsSettings
}
