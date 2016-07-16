name := "InsulatingDomainLogic"

version := "1.0"

scalaVersion := "2.11.8"

scalacOptions ++= List(
  "-Ybackend:GenBCode",
  "-Ydelambdafy:method",
  "-target:jvm-1.8"
)

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

useJCenter := true

//SBT uses the ++= operator to concat the new sequence of dependencies onto the
//existing libarryDependencies
libraryDependencies ++= Seq(
  //scala-reflect is useful for desugaring expressions
  "org.typelevel" %% "cats" % "0.6.1",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  //Required for java8 compatibility in Scala 2.11 - remove after upgrading to 2.12
  "org.scala-lang.modules" %% "scala-java8-compat" % "0.7.0",
  "org.scalatest" %% "scalatest" % "3.0.0-RC4" % "test"
)
