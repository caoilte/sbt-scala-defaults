import scala.util.Properties

//val externalProjectBuilderPlugin =
//  ProjectRef(
//    Properties
//      .envOrNone("WORKING_SCALA")
//      .map(dir => {
//        println(dir)
//        uri(s"file://$dir/sbt-external-project-builder/")
//      })
//      .get,
//    "sbt-external-project-builder"
//  )
//lazy val root = (project in file(".")).dependsOn(externalProjectBuilderPlugin)

addSbtPlugin("org.caoilte" % "sbt-import-scala-files" % "1.0.0")