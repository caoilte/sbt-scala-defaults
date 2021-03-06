package org.caoilte.sbt.defaults

import java.net.UnknownHostException
import java.nio.file.Files
import java.nio.file.attribute.PosixFilePermissions

import fansi.Str
import sbt._
import sbt.Keys._
import fansi.Color._

import scala.io.Source
import scala.util.Failure
import scala.util.Success
import scala.util.Try

object ScalafmtDefaultsPlugin extends AutoPlugin {
  override def requires = org.scalafmt.sbt.ScalafmtPlugin

  override def trigger = allRequirements

  object autoImport {
    val scalafmtGenerateConfig = settingKey[Unit](
      "Setting that writes the sbt-scalafmt-defaults config to the project .scalafmt.conf"
    )
    val scalafmtInstallGitHook = settingKey[Unit](
      "Setting that downloads Scalafmt to a local directory"
    )
    val scalafmt = taskKey[Unit]("Format the source code with scalafmt")
    val scalafmtTest = taskKey[Unit](
      "Fail if any of source code has not been formatted by scalafmt."
    )
  }

  import autoImport._

  override lazy val buildSettings =
    Seq(
      scalafmtGenerateConfig := {
        IO.write(
          file(".scalafmt.conf"),
          """style = defaultWithAlign
            |maxColumn = 112
            |rewrite.rules = [RedundantParens, PreferCurlyFors, AvoidInfix, ExpandImportSelectors, RedundantBraces]
            |
            |align.openParenCallSite = false
            |danglingParentheses = true
            |assumeStandardLibraryStripMargin = true
          """.stripMargin.getBytes("UTF-8")
        )
        sLog.value.info(configWrittenMessage)
      },
      scalafmtInstallGitHook := {
        val GIT_HOOK_DIR      = baseDirectory.value / ".git/hooks"
        val PRE_COMMIT_FILE   = GIT_HOOK_DIR / "pre-commit"
        val SCALAFMT_HOME     = file(s"${System.getProperty("user.home")}/.scalafmt-bin")
        val NAILGUN_CHECK_DIR = SCALAFMT_HOME / "nailgun"

        if (GIT_HOOK_DIR.exists() && GIT_HOOK_DIR.isDirectory) {
          IO.write( // writes to file once when build is loaded
            PRE_COMMIT_FILE,
            s"""#!/usr/bin/env bash
               |${SCALAFMT_HOME.getAbsolutePath}/scalafmt_ng --version ${org.caoilte.sbt.V.scalafmt} --diff --test
            """.stripMargin.getBytes("UTF-8")
          )
          Files.setPosixFilePermissions(PRE_COMMIT_FILE.toPath, PosixFilePermissions.fromString("rwxr-xr-x"))

          sLog.value.info(
            logMessage(
              (Str("Pre-commit hook that forbids unformatted files written to '") ++ Green(
                ".git/hooks/pre-commit"
              ) ++ Str(
                "'"
              )).render
            )
          )
        }

        if (!NAILGUN_CHECK_DIR.exists()) {
          IO.createDirectory(NAILGUN_CHECK_DIR)
        }

        val _ = tryAndDownloadScalafmtNg(SCALAFMT_HOME / "scalafmt_ng").left.foreach { message =>
          sLog.value.warn(message)
        }
      }
    )

  val REMOTE_SCALAFMT_NG = "https://raw.githubusercontent.com/olafurpg/scalafmt/master/bin/scalafmt_ng"
  def tryAndDownloadScalafmtNg(fileLocation: File): Either[String, Unit] =
    Try {
      IO.write( // writes to file once when build is loaded
        fileLocation,
        Source
          .fromURL(REMOTE_SCALAFMT_NG)
          .mkString
          .getBytes("UTF-8")
      )
      Files.setPosixFilePermissions(fileLocation.toPath, PosixFilePermissions.fromString("rwxr-xr-x"))
    } match {
      case Success(p) => Right(())
      case Failure(e) =>
        e match {
          case u: UnknownHostException =>
            Left(
              (Yellow("Failed to connect to '") ++ Str(REMOTE_SCALAFMT_NG)
                ++ Yellow("' in order to create/update local copy")).render
            )
          case other => throw other
        }
    }

  def logMessage(message: String): String =
    (Cyan("scalafmt-defaults") ++ Str(": ")).render + message

  val configWrittenMessage: String = {
    logMessage(
      (Str("config file written to '") ++ Green(
        ".scalafmt.conf"
      ) ++ White("'")).render
    )
  }

}
