import scala.util.matching.Regex

object Regular {
    def main(args: Array[String]) {

	val line = "This is the line that will be tested on"
	println(line)

	val pattern = "tested".r
	val pattern2 = line.r

	println("Looking for the word tested...")
	println(pattern findFirstIn line)

	println("Replacing the whole line with Finished...")
	println(pattern2 replaceFirstIn(line, "Finished")) 
    }
}
 	
	
