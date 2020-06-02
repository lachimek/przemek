package main;

public class Context {
    private final static Context instance = new Context();
    public String selectedSymbol;
    public int selectedYear;
    public int selectedCzynsz;
    public String selectedStolowka;
    public int selectedMonth;
    public String selectedUser;
    public int selectedFloor;
    public int selectedRoom;



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

    private OptionDataPair optionDataPair = new OptionDataPair();
    public OptionDataPair getOptionDataPair(){ return optionDataPair; }

    private Accommodation accommodation = new Accommodation();
    public void setAccommodation(Accommodation accommodation) { this.accommodation = accommodation; }
    public Accommodation getAccommodation() { return accommodation; }

    private PaymentHistory paymentHistory = new PaymentHistory();
    public void setPaymentHistory(PaymentHistory paymentHistory) { this.paymentHistory = paymentHistory; }
    public PaymentHistory getPaymentHistory() { return paymentHistory; }
}
