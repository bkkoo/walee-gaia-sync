name := """walee-gaia-sync"""

version := "1.0"

scalaVersion := "2.11.1"

resolvers ++= Seq(
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/maven-releases/"
)

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "org.scalatest" %% "scalatest" % "2.1.6" % "test",
  "net.sourceforge.jtds" % "jtds" % "1.3.1",
  "com.typesafe.slick" %% "slick" % "2.1.0",
  "com.typesafe.slick" %% "slick-extensions" % "2.1.0",
  "com.typesafe.akka" % "akka-actor_2.11" % "2.3.3",
  "com.google.inject" % "guice" % "4.0-beta4",
  "net.codingwell" %% "scala-guice" % "4.0.0-beta4"
)


fork := true

javaOptions += "-Xmx2G"

mainClass in (Compile, run) := Some("net.shantitree.walee.gaia.sync.Main")


parallelExecution := true

scalacOptions ++= Seq(
  "-feature",
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-Ywarn-dead-code",
  "-language:_",
  "-target:jvm-1.7",
  "-encoding", "UTF-8"
)
