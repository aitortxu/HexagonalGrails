package hexagonalgrails

class Author {

	String name

    static hasMany = [books: Book]

    static constraints = {
    	name minSize: 2
    }
}
