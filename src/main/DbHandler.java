package main;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbHandler {
    private final String connectionUri = "jdbc:mysql://localhost/przemek"; //przemek to nazwa bazy w phpmyadmin
    private Connection conn;
    public DbHandler(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(connectionUri, "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean loginUser(String login, String pass){
        String query = "SELECT id FROM uzytkownicy WHERE Login=? AND Pass=?";
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, pass);
            ResultSet set = statement.executeQuery();
            if(set.first()){// jesli set ma pierwszy obiekt to znaczy ze user istnieje
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<Student> getAllStudents(){
        String query = "select studenci.id, studenci.nazwisko, studenci.imie, studenci.dataurodzenia as data_urodzenia, studenci.nrtelefonu as nr_telefonu, studenci.adresemail as adres_email, studenci.adreszamieszkania as adres_zamieszkania, kierunekstudiow.symbolkierunku as symbol_kierunku, rokstudiow.rokstudiow as rok_studiow, czynsz.wysokoscczynszu as wysokosc_czynszu, stolowka.rodzaj as rodzaj_stolowki from studenci left join kierunekstudiow on studenci.kierunekstudiow_id = kierunekstudiow.id left join rokstudiow on studenci.rokstudiow_id = rokstudiow.id left join czynsz on studenci.czynsz_id = czynsz.id left join stolowka on studenci.stolowka_id = stolowka.id order by studenci.id asc";
        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<Student> data = FXCollections.observableArrayList();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                Student student = new Student();
                student.id.set(rs.getInt("id"));
                student.nazwisko.set(rs.getString("nazwisko"));
                student.imie.set(rs.getString("imie"));
                student.dataUrodzenia.set(rs.getDate("data_urodzenia"));
                student.nrTelefonu.set(rs.getInt("nr_telefonu"));
                student.email.set(rs.getString("adres_email"));
                student.adres.set(rs.getString("adres_zamieszkania"));
                student.kierunek.set(rs.getString("symbol_kierunku"));
                student.rokStudiow.set(rs.getInt("rok_studiow"));
                student.czynsz.set(rs.getInt("wysokosc_czynszu"));
                student.rodzajStolowki.set(rs.getString("rodzaj_stolowki"));
                data.add(student);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return data;
    }

    public ObservableList<Student> getStudentsByProfile(String profileName){
        String query = "select studenci.id, studenci.nazwisko, studenci.imie, studenci.dataurodzenia as data_urodzenia, studenci.nrtelefonu as nr_telefonu, studenci.adresemail as adres_email, studenci.adreszamieszkania as adres_zamieszkania, kierunekstudiow.symbolkierunku as symbol_kierunku, rokstudiow.rokstudiow as rok_studiow, czynsz.wysokoscczynszu as wysokosc_czynszu, stolowka.rodzaj as rodzaj_stolowki from studenci left join kierunekstudiow on studenci.kierunekstudiow_id = kierunekstudiow.id left join rokstudiow on studenci.rokstudiow_id = rokstudiow.id left join czynsz on studenci.czynsz_id = czynsz.id left join stolowka on studenci.stolowka_id = stolowka.id where binary symbolkierunku = ? order by studenci.id asc;";
        PreparedStatement statement = null;
        ResultSet rs = null;
        ObservableList<Student> data = FXCollections.observableArrayList();
        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, profileName);
            rs = statement.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.id.set(rs.getInt("id"));
                student.nazwisko.set(rs.getString("nazwisko"));
                student.imie.set(rs.getString("imie"));
                student.dataUrodzenia.set(rs.getDate("data_urodzenia"));
                student.nrTelefonu.set(rs.getInt("nr_telefonu"));
                student.email.set(rs.getString("adres_email"));
                student.adres.set(rs.getString("adres_zamieszkania"));
                student.kierunek.set(rs.getString("symbol_kierunku"));
                student.rokStudiow.set(rs.getInt("rok_studiow"));
                student.czynsz.set(rs.getInt("wysokosc_czynszu"));
                student.rodzajStolowki.set(rs.getString("rodzaj_stolowki"));
                data.add(student);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return data;
    }

    public ObservableList<Student> getStudentsByYear(int year){
        String query = "select studenci.id, studenci.nazwisko, studenci.imie, studenci.dataurodzenia as data_urodzenia, studenci.nrtelefonu as nr_telefonu, studenci.adresemail as adres_email, studenci.adreszamieszkania as adres_zamieszkania, kierunekstudiow.symbolkierunku as symbol_kierunku, rokstudiow.rokstudiow as rok_studiow, czynsz.wysokoscczynszu as wysokosc_czynszu, stolowka.rodzaj as rodzaj_stolowki from studenci left join kierunekstudiow on studenci.kierunekstudiow_id = kierunekstudiow.id left join rokstudiow on studenci.rokstudiow_id = rokstudiow.id left join czynsz on studenci.czynsz_id = czynsz.id left join stolowka on studenci.stolowka_id = stolowka.id where rokstudiow = ? order by studenci.id asc;";
        PreparedStatement statement = null;
        ResultSet rs = null;
        ObservableList<Student> data = FXCollections.observableArrayList();
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, year);
            rs = statement.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.id.set(rs.getInt("id"));
                student.nazwisko.set(rs.getString("nazwisko"));
                student.imie.set(rs.getString("imie"));
                student.dataUrodzenia.set(rs.getDate("data_urodzenia"));
                student.nrTelefonu.set(rs.getInt("nr_telefonu"));
                student.email.set(rs.getString("adres_email"));
                student.adres.set(rs.getString("adres_zamieszkania"));
                student.kierunek.set(rs.getString("symbol_kierunku"));
                student.rokStudiow.set(rs.getInt("rok_studiow"));
                student.czynsz.set(rs.getInt("wysokosc_czynszu"));
                student.rodzajStolowki.set(rs.getString("rodzaj_stolowki"));
                data.add(student);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return data;
    }

    public ObservableList<Student> getStudentsByCzynsz(int czynsz){
        String query = "select studenci.id, studenci.nazwisko, studenci.imie, studenci.dataurodzenia as data_urodzenia, studenci.nrtelefonu as nr_telefonu, studenci.adresemail as adres_email, studenci.adreszamieszkania as adres_zamieszkania, kierunekstudiow.symbolkierunku as symbol_kierunku, rokstudiow.rokstudiow as rok_studiow, czynsz.wysokoscczynszu as wysokosc_czynszu, stolowka.rodzaj as rodzaj_stolowki from studenci left join kierunekstudiow on studenci.kierunekstudiow_id = kierunekstudiow.id left join rokstudiow on studenci.rokstudiow_id = rokstudiow.id left join czynsz on studenci.czynsz_id = czynsz.id left join stolowka on studenci.stolowka_id = stolowka.id where wysokoscczynszu = ? order by studenci.id asc;";
        PreparedStatement statement = null;
        ResultSet rs = null;
        ObservableList<Student> data = FXCollections.observableArrayList();
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, czynsz);
            rs = statement.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.id.set(rs.getInt("id"));
                student.nazwisko.set(rs.getString("nazwisko"));
                student.imie.set(rs.getString("imie"));
                student.dataUrodzenia.set(rs.getDate("data_urodzenia"));
                student.nrTelefonu.set(rs.getInt("nr_telefonu"));
                student.email.set(rs.getString("adres_email"));
                student.adres.set(rs.getString("adres_zamieszkania"));
                student.kierunek.set(rs.getString("symbol_kierunku"));
                student.rokStudiow.set(rs.getInt("rok_studiow"));
                student.czynsz.set(rs.getInt("wysokosc_czynszu"));
                student.rodzajStolowki.set(rs.getString("rodzaj_stolowki"));
                data.add(student);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return data;
    }

    public ObservableList<Student> getStudentsByStolowka(String stolowka){
        String query = "select studenci.id, studenci.nazwisko, studenci.imie, studenci.dataurodzenia as data_urodzenia, studenci.nrtelefonu as nr_telefonu, studenci.adresemail as adres_email, studenci.adreszamieszkania as adres_zamieszkania, kierunekstudiow.symbolkierunku as symbol_kierunku, rokstudiow.rokstudiow as rok_studiow, czynsz.wysokoscczynszu as wysokosc_czynszu, stolowka.rodzaj as rodzaj_stolowki from studenci left join kierunekstudiow on studenci.kierunekstudiow_id = kierunekstudiow.id left join rokstudiow on studenci.rokstudiow_id = rokstudiow.id left join czynsz on studenci.czynsz_id = czynsz.id left join stolowka on studenci.stolowka_id = stolowka.id where rodzaj = ? order by studenci.id asc;";
        PreparedStatement statement = null;
        ResultSet rs = null;
        ObservableList<Student> data = FXCollections.observableArrayList();
        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, stolowka);
            rs = statement.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.id.set(rs.getInt("id"));
                student.nazwisko.set(rs.getString("nazwisko"));
                student.imie.set(rs.getString("imie"));
                student.dataUrodzenia.set(rs.getDate("data_urodzenia"));
                student.nrTelefonu.set(rs.getInt("nr_telefonu"));
                student.email.set(rs.getString("adres_email"));
                student.adres.set(rs.getString("adres_zamieszkania"));
                student.kierunek.set(rs.getString("symbol_kierunku"));
                student.rokStudiow.set(rs.getInt("rok_studiow"));
                student.czynsz.set(rs.getInt("wysokosc_czynszu"));
                student.rodzajStolowki.set(rs.getString("rodzaj_stolowki"));
                data.add(student);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return data;
    }

    public List<String> getSymbols(){
        String query = "SELECT SymbolKierunku FROM kierunekstudiow";
        List<String> options = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()){
                options.add(rs.getString("SymbolKierunku"));
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return options;
    }

    public List<Integer> getYears(){
        String query = "SELECT RokStudiow FROM rokstudiow";
        List<Integer> options = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()){
                options.add(rs.getInt("RokStudiow"));
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return options;
    }

    public List<Integer> getCzynsze(){
        String query = "SELECT wysokoscCzynszu FROM czynsz";
        List<Integer> options = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()){
                options.add(rs.getInt("wysokoscCzynszu"));
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return options;
    }

    public List<String> getStolowki(){
        String query = "SELECT rodzaj FROM stolowka";
        List<String> options = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()){
                options.add(rs.getString("rodzaj"));
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return options;
    }

    public boolean deleteStudentById(int id){ //todo nie dziala za chuj
        String query = "DELETE FROM studenci WHERE id=?";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addNewStudent(Student student){
        String query = "INSERT INTO `studenci`(`Nazwisko`, `Imie`, `DataUrodzenia`, `NrTelefonu`, `AdresEmail`, `AdresZamieszkania`, `kierunekstudiow_id`, `rokstudiow_id`, `Czynsz_id`, `stolowka_id`) VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, student.nazwisko.get());
            stmt.setString(2, student.imie.get());
            stmt.setDate(3, (Date)student.dataUrodzenia.get());
            stmt.setInt(4, student.nrTelefonu.get());
            stmt.setString(5, student.email.get());
            stmt.setString(6, student.adres.get());
            stmt.setInt(7, (getSymbols().indexOf(student.kierunek.get())+1));
            stmt.setInt(8, (getYears().indexOf(student.rokStudiow.get())+1));
            stmt.setInt(9, (getCzynsze().indexOf(student.czynsz.get())+1));
            stmt.setInt(10, (getStolowki().indexOf(student.rodzajStolowki.get())+1));
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifyStudent(Student student){
        String query = "UPDATE studenci SET Nazwisko = ?, Imie = ?, DataUrodzenia = ?, NrTelefonu = ?, AdresEmail = ?, AdresZamieszkania = ?, kierunekstudiow_id = ?, rokstudiow_id = ?, czynsz_id = ?, stolowka_id = ? WHERE id = ?;";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, student.nazwisko.get());
            stmt.setString(2, student.imie.get());
            stmt.setDate(3, (Date)student.dataUrodzenia.get());
            stmt.setInt(4, student.nrTelefonu.get());
            stmt.setString(5, student.email.get());
            stmt.setString(6, student.adres.get());
            stmt.setInt(7, (getSymbols().indexOf(student.kierunek.get())+1));
            stmt.setInt(8, (getYears().indexOf(student.rokStudiow.get())+1));
            stmt.setInt(9, (getCzynsze().indexOf(student.czynsz.get())+1));
            stmt.setInt(10, (getStolowki().indexOf(student.rodzajStolowki.get())+1));
            stmt.setInt(11, student.id.getValue());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
