name := "Twitter"
 
version := "1.0"
 
scalaVersion := "2.11.8"

assemblyMergeStrategy in assembly := {
       case PathList("META-INF", xs @ _*) => MergeStrategy.discard
       case x => MergeStrategy.first
}

libraryDependencies ++= Seq(
	"org.apache.spark" %% "spark-core" % "2.1.1" % "provided",
	"org.apache.spark" %% "spark-streaming" % "2.1.1" % "provided",
	"org.apache.bahir" %% "spark-streaming-twitter" % "2.1.0",
	"org.twitter4j" % "twitter4j-core" % "4.0.2",
	"org.twitter4j" % "twitter4j-stream" % "4.0.2"
)

resolvers += "Akka Repository" at "http://repo.akka.io/releases/"
