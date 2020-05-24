package main;

public class Context {
    private final static Context instance = new Context();
    public String selectedSymbol;

    public static Context getInstance(){
        return instance;
    }

    private User user = new User();
    public User getUser(){
        return user;
    }

    private DbHandler dbHandler = new DbHandler();
    public DbHandler getDbHandler(){ return dbHandler; }
}
