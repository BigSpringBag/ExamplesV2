
object StrArr {
    def main(args: Array[String]) {

	//Creating an array of strings
	val arr = Array("One", "Two", "Three", "Four")


	//Can add onto the array. +: to prepend and :+ to prepend
	val arr2 = "Zero" +: arr :+ "Five"
	
	//Can call the index of the array
	println(arr2(0))
	println(arr2(5))
	

	//You can turn the array into a string with mkString
	val string = arr2.mkString(" ")

	println(string)

    }
}
	

