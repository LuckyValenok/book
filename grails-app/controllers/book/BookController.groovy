package book

class BookController {
    static defaultAction = "list"

    private final Map<Integer, Book> books = new HashMap<>()

    BookController() {
        books.put(1, new Book(1, 'Test1', 'Test1'))
        books.put(2, new Book(2, 'Test2', 'Test2'))
    }

    def list() {
        render view: 'list', model: [books: books.values()]
    }

    def delete() {
        if (params.id != null) {
            try {
                books.remove(Integer.parseInt(params.id))
            } catch (NumberFormatException ignored) {
            }
        }
        redirect(action: 'list')
    }

    def save() {
        if (params.title != null && params.author != null) {
            int id = books.size() + 1
            books.put(id, new Book(id, params.title, params.author))
        }

        redirect(action: 'list')
    }
}
