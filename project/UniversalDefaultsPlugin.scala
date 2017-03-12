package org.caoilte.sbt.defaults

import sbt._
import sbt.Keys._
import sbt.plugins.JvmPlugin
import org.caoilte.sbt._

object UniversalDefaultsPlugin extends AutoPlugin {
  override def requires = JvmPlugin

  override def trigger = allRequirements

  object autoImport {}

  import autoImport._

  override lazy val projectSettings =
    Seq(
      publishArtifact in (Compile, packageBin) := true,
      publishArtifact in (Test, packageBin) := false,
      publishArtifact in (Compile, packageDoc) := false,
      publishArtifact in (Compile, packageSrc) := true,
      // See: https://github.com/lihaoyi/acyclic/blob/master/readme.md#how-to-use
      scalacOptions += "-P:acyclic:force",
      autoCompilerPlugins := true,
      com.softwaremill.clippy.ClippySbtPlugin.clippyColorsEnabled := true,
      Dependencies.allLibrariesAsDependenciesSetting,
      publishArtifact in Test := false
    ) ++ Dependencies.allCompilerPluginsAsSettings

}