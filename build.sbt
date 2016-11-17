/*
 * Run the "reload" command in the sbt console after changing this file to apply the changes.
 */

// Name of the project
name := "simple-vocabulary-teacher"

// The version of the project
version := "1.0"

// The Scala version to use
scalaVersion := "2.11.7"

// The configuration of the main project
lazy val `simple-vocabulary-teacher` = (project in file(".")).enablePlugins(PlayScala)

// Uses dependency injection in the Play router
routesGenerator := InjectedRoutesGenerator

// The default settings for the scalariform code formatting tool
com.typesafe.sbt.SbtScalariform.scalariformSettings

// Let the router know about the Path Parameters binding
routesImport ++= Seq(
  "binders.PathBinders._",
  "binders.QueryStringBinders._"
)

// Enable filters
libraryDependencies += filters
