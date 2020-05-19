package main;

public class User {
    private boolean loggedIn;
    private String login;
    private String pass;

    User(){
        loggedIn = false;
    }

    public void logIn(String login, String pass){
        this.login = login;
        this.pass = pass;
        this.loggedIn = true;
    }

    public void logout(){
        this.loggedIn = false;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
