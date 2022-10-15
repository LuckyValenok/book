package book

class BookController {
    static defaultAction = "list"

    private final Map<Integer, Book> books = new HashMap<>()

    def beforeInterceptor = [action: this.&checkSession]

    private checkSession() {
        if (!session.user) {
            redirect controller: 'auth', action: 'registration'
            return false
        }
    }

    def list() {
        render view: 'list', model: [books: books.values()]
    }

    def delete(int id) {
        if (session.user.hasPermission(UserPermission.DELETE_BOOK)) {
            books.remove(id)
        }

        redirect action: 'list'
    }

    def save(String title, String author) {
        if (session.user.hasPermission(UserPermission.ADD_BOOK) && title != null && author != null) {
            int id = books.size() + 1
            books.put(id, new Book(id, title, author))
        }

        redirect action: 'list'
    }
}
