import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.SparkContext._
import org.apache.spark.streaming.twitter._
import org.apache.spark.SparkConf
import org.apache.log4j.{Level, Logger}

object Twitter{
	def main(args: Array[String]) {

		println("Twitter Application Running...")
		println("Checking if there are sufficient arguments...")
		if (args.length < 4) {
			System.err.println("Usage: Twitter <consumer key> <consumer secret> <access token> <access token secret> [<filters>]")
			System.exit(1)
		}


		println("Putting arguments into array...")
		val Array(consumerKey, consumerSecret, accessToken, accessTokenSecret) = args.take(4)
		val filters = args.takeRight(args.length - 4)


		println("Setting the arguments as authentication...")
		System.setProperty("twitter4j.oauth.consumerKey", consumerKey)
		System.setProperty("twitter4j.oauth.consumerSecret", consumerSecret)
		System.setProperty("twitter4j.oauth.accessToken", accessToken)
		System.setProperty("twitter4j.oauth.accessTokenSecret", accessTokenSecret)

		println("Creating Spark Configuration...")
		val sc = new SparkConf().setAppName("Twitter")
		val ssc = new StreamingContext(sc, Seconds(3))
		val rootLogger = Logger.getRootLogger()
		rootLogger.setLevel(Level.ERROR)

		println("Creating Discretized Stream...")
		val stream = TwitterUtils.createStream(ssc, None, filters)

		println("Taking tweets...")
		val tweets = stream.map(tweet => tweet.getText())
	
		val lastTweet60 =  tweets.window(Seconds(60))


		println("Taking text that starts with hashtags...")
		val hashTags = stream.flatMap(tweet => tweet.getText.split(" ").filter(_.startsWith("#")))
	
		val topCounts60 = hashTags.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(60))
					.map{case (topic, count) => (count, topic)}

		println("Printing tweets...")
		lastTweet60.foreachRDD(tweet => {
			val topTweet = tweet.take(10)
			println("------------------------------------------------------------------------")
			println("Popular tweets in the last 60 seconds:")
			topTweet.foreach(tweet => println("Tweet: "+tweet)) 
		})
	
		println("Printing hashtags...")
		topCounts60.foreachRDD(hash => {
			val topHash = hash.take(10)
			println("------------------------------------------------------------------------")
			println("Popular hashtags in the last 60 seconds (%s total):".format(hash.count()))
			topHash.foreach{case (count, tag) => println("%s (%s tweets)".format(tag, count))}
		})
	
		ssc.start()
		ssc.awaitTermination()
  
	}
}
