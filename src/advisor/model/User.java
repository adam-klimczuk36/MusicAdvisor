package advisor.model;

public class User {
    boolean logged;

    public User() {
        this.logged = false;
    }

    public void setLogged() {
        this.logged = !logged;
    }

    public boolean isLogged() {
        return logged;
    }
}
