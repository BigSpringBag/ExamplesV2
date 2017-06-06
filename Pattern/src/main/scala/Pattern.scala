object Pattern {

    def patternMatch (x: Any): Any = x match {

	case y: Int => "Passed an integer"
	case z: String => "Passed a string"
        case _ => "Passed something"     

    }

    def main(args: Array[String]) {
	println(patternMatch(5))
	println(patternMatch("Hello"))
	println(patternMatch(23.1))
    }

}

    
