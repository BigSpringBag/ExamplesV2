object Helper {
    def numberPrint(x: Int) {

	var i = 0
	while(i <= x) {
		println("The helper function printed out: " + i)
		i = i + 1
	}	
    }

    def main(args: Array[String]) {
	val number = 5

	numberPrint(number)

    }
}


