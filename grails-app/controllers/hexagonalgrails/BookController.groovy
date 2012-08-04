package hexagonalgrails

import org.springframework.dao.DataIntegrityViolationException

class BookController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def bookModel = new BookModel()

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [bookInstanceList: bookModel.list(params), bookInstanceTotal: bookModel.count()]
    }

    def create() {
        [bookInstance: new Book(params)]
    }

    def save() {
        bookModel.save(params, this)
    }
    def savingError(bookInstance){
        render(view: "create", model: [bookInstance: bookInstance])
    }
    def savingOk(bookInstance){
        flash.message = message(code: 'default.created.message', args: [message(code: 'book.label', default: 'Book'), bookInstance.id])
        redirect(action: "show", id: bookInstance.id)
    }


    def show(Long id) {
        [bookInstance: bookModel.show(id, this)]
    }

    def showingError(Long id){
        flash.message = message(code: 'default.not.found.message', args: [message(code: 'book.label', default: 'Book'), id])
        redirect(action: "list")       
    }

    def edit(Long id) {
        [bookInstance: bookModel.edit(id, this)]
    }
    def editingError(Long id){
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'book.label', default: 'Book'), id])
            redirect(action: "list")
            return        
    }

    def update(Long id, Long version) {
        bookModel.update(id,version,params,this)
    }
    def notFound(bookInstance){
        flash.message = message(code: 'default.not.found.message', args: [message(code: 'book.label', default: 'Book'), bookInstance?.id])
        redirect(action: "list")
    }
    def updatingOk(bookInstance){
        flash.message = message(code: 'default.updated.message', args: [message(code: 'book.label', default: 'Book'), bookInstance.id])
        redirect(action: "show", id: bookInstance.id)
    }
    def updatingError(bookInstance){
        render(view: "edit", model: [bookInstance: bookInstance])
    }
    def updatingVersionError(bookInstance){
        bookInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                  [message(code: 'book.label', default: 'Book')] as Object[],
                  "Another user has updated this Book while you were editing")
        render(view: "edit", model: [bookInstance: bookInstance])
        return  
    }

    def delete(Long id) {
        bookModel.delete(id,this)
    }
    def deletingOk(bookInstance){
        flash.message = message(code: 'default.deleted.message', args: [message(code: 'book.label', default: 'Book'), bookInstance.id])
        redirect(action: "list")
    }
    def deletingError(bookInstance){
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'book.label', default: 'Book'), bookInstance.id])
            redirect(action: "show", id: id)
    }
}
