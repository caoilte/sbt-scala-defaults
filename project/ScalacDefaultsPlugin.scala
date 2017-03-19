package org.caoilte.sbt.defaults

import sbt._
import sbt.Keys.libraryDependencies
import sbt.Keys.scalaVersion
import sbt.Keys._

object ScalacDefaultsPlugin extends AutoPlugin {
  override def requires = sbt.plugins.JvmPlugin

  override def trigger = allRequirements

  val scalacOptions210 = Seq(
    // Standard Options
    // See: https://github.com/scala/scala/blob/2.10.x/src/manual/scala/man1/scalac.scala#L57
    "-deprecation", // Emit warning and location for usages of deprecated APIs.
    "-encoding",
    "utf8", // Specify character encoding used by source files.
    "-feature", // Emit warning and location for usages of features that should be imported explicitly.
    "-target:jvm-1.7", // target JVM 1.7
    "-unchecked",      // Enable detailed unchecked (erasure) warnings

    // Advanced Options
    // See: https://github.com/scala/scala/blob/2.10.x/src/manual/scala/man1/scalac.scala#L182
    "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
    "-Xfuture", // Turn on future language features.
    "-Xlint",   // Enable recommended additional warnings. (scalac -Xlint:help for details)

    // Non-lint warnings
    // See: https://github.com/scala/scala/blob/2.10.x/src/compiler/scala/tools/nsc/settings/Warnings.scala#L18
    "-Ywarn-dead-code", // Warn when dead code is identified.
    "-Ywarn-value-discard", // Warn when non-Unit expression results are unused.
    "-Ywarn-numeric-widen", // Warn when numerics are widened.

    // -Y "Private" settings
    // See: https://github.com/scala/scala/blob/2.10.x/src/compiler/scala/tools/nsc/settings/ScalaSettings.scala#L126
    "-Yinline-warnings", // Emit inlining warnings. (Normally suppressed due to high volume)
    "-Yno-adapted-args" // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
  )

  val scalacOptions211 = Seq(
    // Standard Options
    // See: https://github.com/scala/scala/blob/2.11.x/src/manual/scala/man1/scalac.scala#L53
    "-deprecation", // Emit warning and location for usages of deprecated APIs.
    "-encoding",
    "utf8", // Specify character encoding used by source files.
    "-feature", // Emit warning and location for usages of features that should be imported explicitly.
    "-target:jvm-1.8", // target JVM 1.8
    "-unchecked",      // Enable detailed unchecked (erasure) warnings

    // Advanced Options
    // See: https://github.com/scala/scala/blob/2.11.x/src/manual/scala/man1/scalac.scala#L190
    "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
    "-Xfuture", // Turn on future language features.
    "-Xlint:_", // Enable recommended additional warnings. (scalac -Xlint:help for details)

    // Non-lint warnings
    // See: https://github.com/scala/scala/blob/2.11.x/src/compiler/scala/tools/nsc/settings/Warnings.scala#L20
    "-Ywarn-dead-code", // Warn when dead code is identified.
    "-Ywarn-value-discard", // Warn when non-Unit expression results are unused.
    "-Ywarn-numeric-widen", // Warn when numerics are widened.

    // Language Features
    // See: https://github.com/scala/scala/blob/2.11.x/src/compiler/scala/tools/nsc/settings/ScalaSettings.scala#L72
    "-language:postfixOps", // Allow postfix operator notation, such as `1 to 10 toList'
    "-language:higherKinds",  // Allow higher-kinded types
    "-language:existentials", // Existential types (besides wildcard types) can be written and inferred

    // -Y "Private" settings
    // See: https://github.com/scala/scala/blob/2.11.x/src/compiler/scala/tools/nsc/settings/ScalaSettings.scala#L165
    "-Yinline-warnings", // Emit inlining warnings. (Normally suppressed due to high volume)
    "-Yno-adapted-args", // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.

    // Available in Scala 2.11.9
    "-Yno-adapted-args" // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver. (See: https://issues.scala-lang.org/browse/SI-2712)
  )
  val scalacOptions212 = Seq(
    // Standard Options
    // See: https://github.com/scala/scala/blob/2.12.x/src/manual/scala/man1/scalac.scala#L53
    "-deprecation", // Emit warning and location for usages of deprecated APIs.
    "-encoding",
    "utf8", // Specify character encoding used by source files.
    "-feature", // Emit warning and location for usages of features that should be imported explicitly.
    "-target:jvm-1.8", // target JVM 1.8
    "-unchecked",      // Enable detailed unchecked (erasure) warnings

    // Advanced Options
    // See: https://github.com/scala/scala/blob/2.12.x/src/manual/scala/man1/scalac.scala#L187
    "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
    "-Xfuture", // Turn on future language features.
    "-Xlint:_", // Enable recommended additional warnings. (scalac -Xlint:help for details)

    // Non-lint warnings
    // See: https://github.com/scala/scala/blob/2.12.x/src/compiler/scala/tools/nsc/settings/Warnings.scala#L18
    "-Ywarn-dead-code", // Warn when dead code is identified.
    "-Ywarn-value-discard", // Warn when non-Unit expression results are unused.
    "-Ywarn-numeric-widen",  // Warn when numerics are widened.
    "-Ywarn-extra-implicit", // Warn when more than one implicit parameter section is defined.

    // Language Features
    // See: https://github.com/scala/scala/blob/2.12.x/src/compiler/scala/tools/nsc/settings/ScalaSettings.scala#L63
    "-language:postfixOps", // Allow postfix operator notation, such as `1 to 10 toList'
    "-language:higherKinds",  // Allow higher-kinded types
    "-language:existentials", // Existential types (besides wildcard types) can be written and inferred

    // -Y "Private" settings
    // See: https://github.com/scala/scala/blob/2.12.x/src/compiler/scala/tools/nsc/settings/ScalaSettings.scala#L93
    "-Yno-adapted-args" // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver. (See: https://issues.scala-lang.org/browse/SI-2712)
  )

  def scalacOptionsForVersion(scalaVersion: String) =
    CrossVersion.partialVersion(scalaVersion) match {
      case Some((2, 10)) => scalacOptions210
      case Some((2, 11)) => scalacOptions211
      case Some((2, 12)) => scalacOptions212
      case _             => Nil
    }

  def extraDependenciesForVersion(scalaVersion: String) =
    CrossVersion.partialVersion(scalaVersion) match {
      case Some((2, 10)) => {
        Seq(
          // See: https://github.com/lihaoyi/acyclic/blob/master/readme.md#how-to-use
          "org.scala-lang" % "scala-compiler" % scalaVersion % "provided"
        )
      }
      case _ => Nil
    }

  override lazy val projectSettings = Seq(
    scalacOptions ++= scalacOptionsForVersion(scalaVersion.value),
    libraryDependencies ++= extraDependenciesForVersion(scalaVersion.value)
  )
}
