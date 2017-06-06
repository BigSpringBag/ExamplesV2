// import PrintWriter
import java.io._
import scala.io._

object InAndOut {
  def main(args: Array[String]) {
  
  //read
  val data = scala.io.Source.fromFile("./infile.txt").mkString
  
  //write
  new PrintWriter(new File("./output.txt")){ write(data);close}
  
  }
}
