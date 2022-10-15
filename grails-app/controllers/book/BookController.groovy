package book

class BookController {
    static defaultAction = "list"

    private final Map<Integer, Book> books = new HashMap<>()
    private final Map<String, User> usersByLogin = new HashMap<>()

    def beforeInterceptor = [action: this.&auth, except: ['registration', 'login']]

    private auth() {
        if (!session.user) {
            redirect action: 'registration'
            return false
        }
    }

    def registration() {
        if (session.user) {
            redirect action: 'list'
            return
        }

        String login = request.getParameter('login')
        String email = request.getParameter("email")
        String password = request.getParameter("password")

        if (login == null || usersByLogin.containsKey(login) || email == null || password == null) {
            render view: 'registration'
            return
        }

        User user = [login, password, email]
        usersByLogin.put(login, user)
        session.user = user
        redirect action: 'list'
    }

    def login() {
        if (session.user) {
            redirect action: 'list'
            return
        }

        String login = request.getParameter('login')
        String password = request.getParameter("password")
        User user

        if (login == null || password == null || (user = usersByLogin.get(login)) == null || user.getPassword() != password) {
            render view: 'login'
            return
        }

        session.user = user
        redirect action: 'list'
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
