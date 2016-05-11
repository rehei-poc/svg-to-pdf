name := "svg-to-pdf"

// set version to environment variable $tag 
version := sys.props.getOrElse("tag", default = "0.0.0")

organization := "com.github.rehei"

scalaVersion := "2.11.7"

resolvers += "snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers += "staging" at "https://oss.sonatype.org/content/repositories/staging"
resolvers += "releases" at "https://oss.sonatype.org/content/repositories/releases"
resolvers += Resolver.bintrayRepo("rehei", "maven")

unmanagedResourceDirectories in Test <+= (baseDirectory) { _ / "src/main/webapp" }

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

libraryDependencies ++= {
  Seq(
     "org.apache.xmlgraphics" % "fop" % "2.0",
     "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.5"
  )
}

EclipseKeys.withSource := true

