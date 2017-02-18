package org.caoilte.sbt.defaults

import fansi.Str
import sbt.Keys._
import sbt.{AutoPlugin, _}
import sbt.plugins.JvmPlugin
import org.scalafmt.bootstrap.ScalafmtBootstrap
import org.scalafmt.Versions

object SetupScalafmtPlugin extends AutoPlugin {
  override def requires = JvmPlugin

  override def trigger = allRequirements

  object autoImport {
    lazy val scalafmtGenerateConfig = settingKey[Unit](
      "Setting that writes the sbt-scalafmt-defaults config to the project .scalafmt.conf"
    )
    lazy val scalafmt = taskKey[Unit]("Format the source code with scalafmt")
    lazy val scalafmtTest = taskKey[Unit](
      "Fail if any of source code has not been formatted by scalafmt."
    )
  }

  import autoImport._

  override lazy val globalSettings =
    Seq(
      scalafmtGenerateConfig := {
        IO.write( // writes to file once when build is loaded
          file(".scalafmt.conf"),
          """style = IntelliJ
          |# Your configuration here
          |rewrite.rules = [SortImports, RedundantParens, SortImports, PreferCurlyFors]
        """.stripMargin.getBytes("UTF-8")
        )
        sLog.value.info(configWrittenMessage)
      },
      scalafmt := runScalaFmt(),
      scalafmtTest := runScalaFmt("--test")
    )

  def runScalaFmt(args: String*): Unit = {
    try {
      ScalafmtBootstrap.main(args)
      println()
    } catch {
      case e: java.lang.NoSuchMethodError
          if e.getMessage.startsWith("coursier") =>
        System.err.println(
          "Error. Found conflicting version of coursier, sbt-scalafmt requires" +
            s" coursier version ${Versions.coursier}."
        )
    }
  }

  val configWrittenMessage: String = {
    import fansi.Color._
    (Str("sbt-scalafmt-defaults config written to '") ++ Green(
      ".scalafmt.conf"
    ) ++ White("'")).render
  }

}
