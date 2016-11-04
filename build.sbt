# Name of the project
name := "simple-vocabulary-teacher"

# The version of the project
version := "1.0"

# The Scala version to use
scalaVersion := 2.11.7

# The configuration of the main project
lazy val `simple-vocabulary-teacher` = 
  (project in file(".")).enablePlugins(playScala)

# Uses dependency injection in the Play router
routesGenerator := InjectedRoutesGenerator

# The default settings for the scalariform code formatting tool
com.typesafe.sbt.SbtScalariform.scalariformSettings
