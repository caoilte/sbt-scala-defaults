package org.caoilte.sbt.defaults.sensible

import sbt.AutoPlugin

object SensiblePluginsPlugin extends AutoPlugin {
  override def requires = sbt.plugins.JvmPlugin

  override def trigger = allRequirements

  override lazy val buildSettings = Seq()
}
