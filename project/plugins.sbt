filesToImport := Seq(file("project/project/Dependencies.scala"))

org.caoilte.sbt.Dependencies.baseLibrariesAndPluginsAsSettings

org.caoilte.sbt.SBTPlugins.forOpensourceProjects.map(addSbtPlugin)