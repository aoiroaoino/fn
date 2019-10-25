ThisBuild / organization := "com.github.aoiroaoino"
ThisBuild / name         := "fn"
ThisBuild / scalaVersion := "2.13.1"

scalacOptions ++= Seq(
  "-feature",
  "-language:higherKinds,implicitConversions"
)

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.1" % Test