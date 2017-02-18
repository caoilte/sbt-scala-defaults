package org.caoilte.sbt.defaults

import sbt._
import sbt.Keys._
import com.typesafe.sbt.JavaVersionCheckPlugin
import com.typesafe.sbt.JavaVersionCheckPlugin.autoImport._

object Scala210DefaultsPlugin extends AutoPlugin {
  override def requires = JavaVersionCheckPlugin

  override def trigger = allRequirements

  override lazy val buildSettings = Seq(
    scalacOptions := Seq(
      // Standard Options
      // See: https://github.com/scala/scala/blob/2.10.x/src/manual/scala/man1/scalac.scala#L57
      "-deprecation", // Emit warning and location for usages of deprecated APIs.
      "-encoding",
      "utf8", // Specify character encoding used by source files.
      "-feature", // Emit warning and location for usages of features that should be imported explicitly.
      "-target:jvm-1.7", // target JVM 1.8
      "-unchecked", // Enable detailed unchecked (erasure) warnings

      // Advanced Options
      // See: https://github.com/scala/scala/blob/2.10.x/src/manual/scala/man1/scalac.scala#L182
      "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
      "-Xfuture", // Turn on future language features.
      "-Xlint", // Enable recommended additional warnings. (scalac -Xlint:help for details)

      // Non-lint warnings
      // See: https://github.com/scala/scala/blob/2.10.x/src/compiler/scala/tools/nsc/settings/Warnings.scala#L18
      "-Ywarn-dead-code", // Warn when dead code is identified.
      "-Ywarn-value-discard", // Warn when non-Unit expression results are unused.
      "-Ywarn-numeric-widen", // Warn when numerics are widened.

      // -Y "Private" settings
      // See: https://github.com/scala/scala/blob/2.10.x/src/compiler/scala/tools/nsc/settings/ScalaSettings.scala#L126
      "-Yinline-warnings", // Emit inlining warnings. (Normally suppressed due to high volume)
      "-Yno-adapted-args" // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
    ),
    scalaVersion := "2.10.6",
    javaVersionPrefix in javaVersionCheck := Some("1.7"),
    libraryDependencies ++= Seq(
      // See: https://github.com/lihaoyi/acyclic/blob/master/readme.md#how-to-use
      "org.scala-lang" % "scala-compiler" % scalaVersion.value % "provided"
    )
  )
}
