package org.caoilte

import sbt._
import sbt.Keys._

object ScalaDefaultsPlugin extends AutoPlugin {
  override def requires = sbt.plugins.JvmPlugin

  override def trigger = allRequirements

  override lazy val buildSettings = Seq(
    scalacOptions := Seq(
      // Standard Options
      // See: https://github.com/scala/scala/blob/2.11.x/src/manual/scala/man1/scalac.scala#L53
      "-deprecation", // Emit warning and location for usages of deprecated APIs.
      "-encoding", "utf8", // Specify character encoding used by source files.
      "-feature", // Emit warning and location for usages of features that should be imported explicitly.
      "-target:jvm-1.8", // target JVM 1.8
      "-unchecked", // Enable detailed unchecked (erasure) warnings

      // Advanced Options
      // See: https://github.com/scala/scala/blob/2.11.x/src/manual/scala/man1/scalac.scala#L190
      "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
      "-Xfuture", // Turn on future language features.
      "-Xlint", // Enable recommended additional warnings.

      // Non-lint warnings
      // See: https://github.com/scala/scala/blob/2.11.x/src/compiler/scala/tools/nsc/settings/Warnings.scala#L20
      "-Ywarn-dead-code", // Warn when dead code is identified.
      "-Ywarn-value-discard", // Warn when non-Unit expression results are unused.
      "-Ywarn-numeric-widen", // Warn when numerics are widened.

      // Language Features
      // See: https://github.com/scala/scala/blob/2.11.x/src/compiler/scala/tools/nsc/settings/ScalaSettings.scala#L72
      "-language:postfixOps", // Allow postfix operator notation, such as `1 to 10 toList'
      "-language:higherKinds", // Allow higher-kinded types
      "-language:existentials", // Existential types (besides wildcard types) can be written and inferred

      // -Y "Private" settings
      // See: https://github.com/scala/scala/blob/2.11.x/src/compiler/scala/tools/nsc/settings/ScalaSettings.scala#L165
      "-Yinline-warnings", // Emit inlining warnings. (Normally suppressed due to high volume)
      "-Yno-adapted-args"  // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.

    ),
    scalaVersion := "2.11.7"
  )
}
