# SBT Scala Defaults

Less boilerplate!

# Licence

[Apache 2.0][LICENCE]

# Rejected Defaults

# Ideas for the future

## Fix Cross Publishing Multi-Module builds with sbt-doge

Fairly [simples][sbt-doge]

## Enable 2.12 New Optimizer on non-incremental builds

Instructions on how to configure this [here][212-new-optimizer] but no clue how to only do it on releases.

## A task to configure sensible global plugins

eg

- https://github.com/sbt/sbt-duplicates-finder
- sbt-updates

## Compare sbt-multi-jvm with sbt-cotest

https://github.com/sbt/sbt-multi-jvm
https://github.com/sbt/sbt-cotest


## A version of sbt-javaversioncheck that specifies a minimum version

Because [sbt-versioncheck] forces you to use **exactly** the version of Java that you specify it is not very useful as a default plugin in most situations. It is temporarily useful for Scala 2.12 but I will probably have to remove it once Java9 comes out. 

If we could specify a version range that would be more useful.

## Add javacOptions

If I must.

[LICENCE]: https://github.com/caoilte/sbt-import-scala-files/blob/master/LICENCE "Licence"
[212-new-optimizer]: http://scala-lang.org/news/2.12.0#new-optimizer "Scala 2.12 New Optimizer"
[sbt-versioncheck]: https://github.com/sbt/sbt-javaversioncheck/ "sbt-javaversioncheck"
[sbt-doge]: https://github.com/sbt/sbt-doge "sbt-doge"