package book

class BookController {
    static defaultAction = "list"

    private final Map<Integer, Book> books = new HashMap<>()

    def list() {
        render view: 'list', model: [books: books.values()]
    }

    def delete(int id) {
        books.remove(id)
        redirect(action: 'list')
    }

    def save(String title, String author) {
        if (title != null && author != null) {
            int id = books.size() + 1
            books.put(id, new Book(id, title, author))
        }

        redirect(action: 'list')
    }
}
