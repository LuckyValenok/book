package book

class AuthController {
    static defaultAction = "login"

    private final Map<String, User> usersByLogin = new HashMap<>()

    def beforeInterceptor = [action: this.&checkSession]

    private def checkSession() {
        if (session.user) {
            redirect controller: 'book', action: 'list'
            return false
        }
    }

    def registration() {
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
        redirect controller: 'book', action: 'list'
    }

    def login() {
        String login = request.getParameter('login')
        String password = request.getParameter("password")
        User user

        if (login == null || password == null || (user = usersByLogin.get(login)) == null || user.getPassword() != password) {
            render view: 'login'
            return
        }

        session.user = user
        redirect controller: 'book', action: 'list'
    }
}
