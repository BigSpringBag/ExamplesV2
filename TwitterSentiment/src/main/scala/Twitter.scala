import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.streaming.twitter._
import org.apache.spark.SparkConf
import org.apache.log4j.{Level, Logger}
import scala.io.Source

object Twitter{

	def wordValue(word: String, posWords: Set[String], negWords: Set[String]): Int = {
		if (posWords.contains(word)) 1
		else if (negWords.contains(word)) -1
		else 0
	}
	

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
		val conf = new SparkConf().setAppName("Twitter")
		val sc = new SparkContext(conf)
		val ssc = new StreamingContext(sc, Seconds(2))
	
		//Removing unnecessary INFO lines printed on console
		println("Removing INFO lines from printing on console...")
		val rootLogger = Logger.getRootLogger()
		rootLogger.setLevel(Level.ERROR)

		//Adding Positive/Negative Sentiment Word Files
		println("Taking in Sentiment File...")
		val posFile = scala.io.Source.fromFile("/student/cslec/robertford/Examples/TwitterSentiment/positive_words.txt").getLines.toSet 
		val negFile = scala.io.Source.fromFile("/student/cslec/robertford/Examples/TwitterSentiment/negative_words.txt").getLines.toSet
	
		println("Broadcasting Sentiment Words to all Worker Nodes...")
		val posWords = sc.broadcast(posFile)
		val negWords = sc.broadcast(negFile)

		println("Creating Discretized Stream...")
		val stream = TwitterUtils.createStream(ssc, None, filters)

		println("Taking tweets...")
                val tweets = stream.map(tweet => tweet.getText())
                val lastTweet60 =  tweets.window(Seconds(60))

		println("Taking text that starts with hashtags...")
		val hashTags = stream.flatMap(tweet => tweet.getText.split(" ").filter(_.startsWith("#")))
		
		println("Taking words within tweets...")
		val tweetWords = stream.flatMap(tweet => tweet.getText.split(" ").map(_.toLowerCase).filter(_.matches("[a-z]+")))
		val tweetWords60 = tweetWords.window(Seconds(60))
	
		val topCounts60 = hashTags.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(60))
					.map{case (topic, count) => (count, topic)}

		println("Printing tweet words...")
		tweetWords60.foreachRDD(words => {
			val topWords = words.take(100)
			println("------------------------------------------------------------------------")
			println("Words within tweets:")
			topWords.foreach(word => println("Word: "+wordValue(word, posFile, negFile)))
			println("Total Sentiment Score: "+topWords.map(word => wordValue(word, posFile, negFile)).sum)	
		})

		println("Printing tweets...")
                lastTweet60.foreachRDD(tweet => {
                        val topTweet = tweet.take(10)
			println("------------------------------------------------------------------------")
                        println("Popular tweets in the last 60 seconds:")
                        topTweet.foreach(tweet => println("Tweet: "+tweet))
                })
				
		println("Printing hashtags...")
		topCounts60.foreachRDD(hash => {
			val topList = hash.take(10)
			println("------------------------------------------------------------------------")
			println("Popular hashtags in the last 60 seconds (%s total):".format(hash.count()))
			topList.foreach{case (count, tag) => println("%s (%s tweets)".format(tag, count))}
		})

		ssc.start()
		ssc.awaitTermination()
  
	}
}
