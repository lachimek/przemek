package main;

public class Context {
    private final static Context instance = new Context();
    public String selectedSymbol;
    public int selectedYear;
    public int selectedCzynsz;
    public String selectedStolowka;
    public int selectedMonth;
    public String selectedUser;

    public static Context getInstance(){
        return instance;
    }

    private User user = new User();
    public User getUser(){
        return user;
    }

    private Student student = new Student();
    public Student getStudent(){
        return student;
    }
    public void setStudent(Student s) {student = s; }

    private DbHandler dbHandler = new DbHandler();
    public DbHandler getDbHandler(){ return dbHandler; }
}
