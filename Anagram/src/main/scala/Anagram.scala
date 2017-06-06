// import PrintWriter
import java.io._

// import required spark classes
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object Anagram {
  def main(args: Array[String]) {

    val conf = new SparkConf().setAppName("Anagram Application")
    val sc = new SparkContext(conf)
    
    val html = scala.io.Source.fromURL("https://cs.utm.utoronto.ca/~yanyutao/files/texthttp.txt").mkString
    val arr = html.split("\n")
    val rdds = sc.parallelize(arr,4)

    rdds.foreach(word => println(word))
    rdds.foreach(line => println("-------------------------"))
    
    val counts = rdds.map(word => (word.toLowerCase.toCharArray.toList.sorted.mkString, 1)).reduceByKey(_ + _)
    
    counts.foreach(word => println(word))

    val countCollect = counts.collect()
    new PrintWriter(new File("/student/cslec/robertford/Examples/Anagram/result.txt")) for (line <- countCollect) write(line.toString()+"\n");close}

    sc.stop()    
  }
}
