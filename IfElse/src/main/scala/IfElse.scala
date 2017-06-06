object IfElse {

    def main(args: Array[String]) {
	
	var x = 4

	println("Guess a integer from 1 to 10")
	val number = readInt()

	if(number > 10 || number < 1) {
		println("Out of bounds")

	} else if(number < x) {

		println("Guessed too low")

	} else if (number > x) {

		println("Guessed too high")

	} else if (number == x) {

		println("You guessed correctly")

	}

    }

}

	
