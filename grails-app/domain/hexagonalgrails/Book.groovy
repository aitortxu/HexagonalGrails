package hexagonalgrails

class Book {

	String title
   	
   	static belongsTo = [author: Author]

    static constraints = {
    	title minSize: 2
    }
}
