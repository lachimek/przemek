package main;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

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

    public ResultSet getAllStudents(){
        String query = "select studenci.id, studenci.nazwisko, studenci.imie, studenci.dataurodzenia as data_urodzenia, studenci.nrtelefonu as nr_telefonu, studenci.adresemail as adres_email, studenci.adreszamieszkania as adres_zamieszkania, kierunekstudiow.symbolkierunku as symbol_kierunku, rokstudiow.rokstudiow as rok_studiow, czynsz.wysokoscczynszu as wysokosc_czynszu, stolowka.rodzaj as rodzaj_stolowki from studenci left join kierunekstudiow on studenci.kierunekstudiow_id = kierunekstudiow.id left join rokstudiow on studenci.rokstudiow_id = rokstudiow.id left join czynsz on studenci.czynsz_id = czynsz.id left join stolowka on studenci.stolowka_id = stolowka.id order by studenci.id asc";
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return rs;
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

    public ResultSet getSymbols(){
        String query = "SELECT SymbolKierunku FROM kierunekstudiow";
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return rs;
    }

    public boolean deleteStudentById(int id){ //todo nie dziala za chuj
        String query = "UPDATE studenci SET KierunekStudiow_id=null, RokStudiow_id=null, Czynsz_id=null, Stolowka_id=null WHERE id=?";
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
}
