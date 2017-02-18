package org.caoilte.sbt.defaults

import sbt._
import sbt.Keys._

object SensiblePluginDefaultsPlugin extends AutoPlugin {
  override def requires = sbt.plugins.JvmPlugin

  override def trigger = allRequirements

  object autoImport {}

  import autoImport._

  override lazy val projectSettings = Seq(sbtPlugin := true)
}
