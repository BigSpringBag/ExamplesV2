
object ForWhile {
    def main(args: Array[String]) {
	
	var i = 0
	var j = 0

	while(i < 10) {
		println("Current number for i is: " + i)
		i = i + 1;
	}

	for(j <- 1 to 10) {
		println("Current number for j is: " + j)
    	}
    }
}
