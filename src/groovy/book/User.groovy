package book

class User {
    String login
    String password
    String email
    final Set<UserPermission> permissions = new HashSet<>()

    User(String login, String password, String email) {
        this.login = login
        this.password = password
        this.email = email
    }

    def hasPermission(UserPermission permission) {
        return permissions.contains(permission)
    }
}
