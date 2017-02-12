package org.caoilte.scalafmt

import org.scalafmt.bootstrap.ScalafmtBootstrap
import sbt._
import sbt.Keys._
import sbt.inc.Analysis
import sbt.plugins.JvmPlugin

/**
  * Stolen from @hseeberger here,
  * https://gist.github.com/olafurpg/e045ef9d8a4273bae3e2ccf610636d66#gistcomment-1984972
  */
object IncrementalScalafmtPlugin extends AutoPlugin {

  override def requires = JvmPlugin

  override def trigger = allRequirements

  override def projectSettings =
    (includeFilter.in(autoImport.scalafmtInc) := "*.scala") +: automateScalafmtFor(
      Compile,
      Test
    )

  object autoImport {
    lazy val scalafmtInc =
      taskKey[Unit]("Incrementally format modified sources")
  }
  import autoImport._

  def automateScalafmtFor(configurations: Configuration*): Seq[Setting[_]] =
    configurations.flatMap { c =>
      inConfig(c)(
        Seq(
          compileInputs.in(compile) := {
            scalafmtInc.value
            compileInputs.in(compile).value
          },
          sourceDirectories.in(scalafmtInc) := Seq(scalaSource.value),
          scalafmtInc := {
            val cache = streams.value.cacheDirectory / "scalafmt"
            val include = includeFilter.in(scalafmtInc).value
            val exclude = excludeFilter.in(scalafmtInc).value
            val sources =
              sourceDirectories
                .in(scalafmtInc)
                .value
                .descendantsExcept(include, exclude)
                .get
                .toSet
            def format(handler: Set[File] => Unit, msg: String) = {
              def update(handler: Set[File] => Unit,
                         msg: String)(in: ChangeReport[File],
                                      out: ChangeReport[File]) = {
                val label = Reference.display(thisProjectRef.value)
                val files = in.modified -- in.removed
                Analysis
                  .counted("Scala source", "", "s", files.size)
                  .foreach(
                    count =>
                      streams.value.log.info(s"$msg $count in $label ...")
                  )
                handler(files)
                files
              }
              FileFunction.cached(cache)(FilesInfo.hash, FilesInfo.exists)(
                update(handler, msg)
              )(sources)
            }
            def formattingHandler(files: Set[File]) =
              if (files.nonEmpty) {
                val filesArg = files.map(_.getAbsolutePath).mkString(",")
                ScalafmtBootstrap.main(
                  List("--non-interactive", "-i", "-f", filesArg)
                )
              }
            format(formattingHandler, "Formatting")
            format(_ => (), "Reformatted") // Recalculate the cache
          }
        )
      )
    }
}
