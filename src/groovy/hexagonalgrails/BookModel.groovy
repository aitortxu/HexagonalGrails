package hexagonalgrails

import org.springframework.dao.DataIntegrityViolationException

class BookModel{
	def list(params){
		Book.list(params)
	}
	def count(){
		Book.count()
	}
	def save(params, listener){
		def bookInstance = new Book(params)
        if (!bookInstance.save(flush: true)) {
            listener.savingError(bookInstance)
        }
        else {
        	listener.savingOk(bookInstance)
        }
	}
	def show(Long id, listener){
		def bookInstance = Book.get(id)
        if (!bookInstance) {
            listener.showingError(id)
        }
        else {
        	bookInstance
        }
	}
	def edit(Long id, listener){
		def bookInstance = Book.get(id)
        if (!bookInstance) {
            listener.editingError(id)
        }
        else {
        	bookInstance
        }
	}
	boolean versionError(version, bookInstance){
		(version != null && bookInstance.version > version)
	}
	def update(id,version,params, listener){
		def bookInstance = Book.get(id)	
	    if (bookInstance) {    
	        if (versionError(version, bookInstance)) {
                listener.updatingVersionError(bookInstance)
	        }
	        else {
		        bookInstance.properties = params

		        if (!bookInstance.save(flush: true)) {
		            listener.updatingError(bookInstance)
		        }
		        else {
		        	listener.updatingOk(bookInstance)
		        }
	        }	
	    }
	    else {
	    	listener.notFound(bookInstance)
	    }
	}
	def delete(id, listener){
        def bookInstance = Book.get(id)
        if (bookInstance) {
        	try {
	            bookInstance.delete(flush: true)
	            listener.deletingOk(bookInstance)
	        }
	        catch (DataIntegrityViolationException e) {
	            listener.deletingError(bookInstance)
	        }
        }
        else {
            listener.notFound(bookInstance)
        }
	}
}