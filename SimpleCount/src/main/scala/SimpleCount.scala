import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf


object SimpleCount {
	def main(args: Array[String]) {
	val logFile = "/student/cslec/robertford/spark-2.1.1-bin-hadoop2.7/README.md"
	val conf = new SparkConf().setAppName("Simple Count")
	val sc = new SparkContext(conf)
	val logData = sc.textFile(logFile)
	val numofAs = logData.filter(line => line.contains("a")).count()
	val numofBs = logData.filter(line => line.contains("b")).count()
	println(s"Lines with a: $numofAs, Lines with b: $numofBs")
	}
}
	
