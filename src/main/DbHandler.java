package main;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
    //Login
    public boolean loginUser(String login, String pass){
        String query = "SELECT id FROM uzytkownicy WHERE BINARY Login=? AND BINARY Pass=?";
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

    public List<String> getUsers(){
        String query = "SELECT Login FROM uzytkownicy";
        List<String> users = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()){
                users.add(rs.getString("Login"));
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return users;
    }
    //End login
    //Manage students
    public ObservableList<Student> getAllStudents(){
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
        return assignResultsetToStudent(rs);
    }

    public ObservableList<Student> getStudentsByProfile(String profileName){
        String query = "select studenci.id, studenci.nazwisko, studenci.imie, studenci.dataurodzenia as data_urodzenia, studenci.nrtelefonu as nr_telefonu, studenci.adresemail as adres_email, studenci.adreszamieszkania as adres_zamieszkania, kierunekstudiow.symbolkierunku as symbol_kierunku, rokstudiow.rokstudiow as rok_studiow, czynsz.wysokoscczynszu as wysokosc_czynszu, stolowka.rodzaj as rodzaj_stolowki from studenci left join kierunekstudiow on studenci.kierunekstudiow_id = kierunekstudiow.id left join rokstudiow on studenci.rokstudiow_id = rokstudiow.id left join czynsz on studenci.czynsz_id = czynsz.id left join stolowka on studenci.stolowka_id = stolowka.id where binary symbolkierunku = ? order by studenci.id asc;";
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, profileName);
            rs = statement.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return assignResultsetToStudent(rs);
    }

    public ObservableList<Student> getStudentsByYear(int year){
        String query = "select studenci.id, studenci.nazwisko, studenci.imie, studenci.dataurodzenia as data_urodzenia, studenci.nrtelefonu as nr_telefonu, studenci.adresemail as adres_email, studenci.adreszamieszkania as adres_zamieszkania, kierunekstudiow.symbolkierunku as symbol_kierunku, rokstudiow.rokstudiow as rok_studiow, czynsz.wysokoscczynszu as wysokosc_czynszu, stolowka.rodzaj as rodzaj_stolowki from studenci left join kierunekstudiow on studenci.kierunekstudiow_id = kierunekstudiow.id left join rokstudiow on studenci.rokstudiow_id = rokstudiow.id left join czynsz on studenci.czynsz_id = czynsz.id left join stolowka on studenci.stolowka_id = stolowka.id where rokstudiow = ? order by studenci.id asc;";
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, year);
            rs = statement.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return assignResultsetToStudent(rs);
    }

    public ObservableList<Student> getStudentsByCzynsz(int czynsz){
        String query = "select studenci.id, studenci.nazwisko, studenci.imie, studenci.dataurodzenia as data_urodzenia, studenci.nrtelefonu as nr_telefonu, studenci.adresemail as adres_email, studenci.adreszamieszkania as adres_zamieszkania, kierunekstudiow.symbolkierunku as symbol_kierunku, rokstudiow.rokstudiow as rok_studiow, czynsz.wysokoscczynszu as wysokosc_czynszu, stolowka.rodzaj as rodzaj_stolowki from studenci left join kierunekstudiow on studenci.kierunekstudiow_id = kierunekstudiow.id left join rokstudiow on studenci.rokstudiow_id = rokstudiow.id left join czynsz on studenci.czynsz_id = czynsz.id left join stolowka on studenci.stolowka_id = stolowka.id where wysokoscczynszu = ? order by studenci.id asc;";
        String queryNull = "select studenci.id, studenci.nazwisko, studenci.imie, studenci.dataurodzenia as data_urodzenia, studenci.nrtelefonu as nr_telefonu, studenci.adresemail as adres_email, studenci.adreszamieszkania as adres_zamieszkania, kierunekstudiow.symbolkierunku as symbol_kierunku, rokstudiow.rokstudiow as rok_studiow, czynsz.wysokoscczynszu as wysokosc_czynszu, stolowka.rodzaj as rodzaj_stolowki from studenci left join kierunekstudiow on studenci.kierunekstudiow_id = kierunekstudiow.id left join rokstudiow on studenci.rokstudiow_id = rokstudiow.id left join czynsz on studenci.czynsz_id = czynsz.id left join stolowka on studenci.stolowka_id = stolowka.id where wysokoscczynszu IS NULL order by studenci.id asc;";
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            if(czynsz != 0){
                statement = conn.prepareStatement(query);
//                System.out.println("Czynsz != 0");
                statement.setInt(1, czynsz);
            }else {
                statement = conn.prepareStatement(queryNull);
            }
            rs = statement.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return assignResultsetToStudent(rs);
    }

    public ObservableList<Student> getStudentsByStolowka(String stolowka){
        String query = "select studenci.id, studenci.nazwisko, studenci.imie, studenci.dataurodzenia as data_urodzenia, studenci.nrtelefonu as nr_telefonu, studenci.adresemail as adres_email, studenci.adreszamieszkania as adres_zamieszkania, kierunekstudiow.symbolkierunku as symbol_kierunku, rokstudiow.rokstudiow as rok_studiow, czynsz.wysokoscczynszu as wysokosc_czynszu, stolowka.rodzaj as rodzaj_stolowki from studenci left join kierunekstudiow on studenci.kierunekstudiow_id = kierunekstudiow.id left join rokstudiow on studenci.rokstudiow_id = rokstudiow.id left join czynsz on studenci.czynsz_id = czynsz.id left join stolowka on studenci.stolowka_id = stolowka.id where rodzaj = ? order by studenci.id asc;";
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, stolowka);
            rs = statement.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return assignResultsetToStudent(rs);
    }

    private ObservableList<Student> assignResultsetToStudent(ResultSet rs){
        ObservableList<Student> data = FXCollections.observableArrayList();
        try {
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
            options.add(0);
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

    public boolean deleteStudentById(int id){
        String query = "DELETE FROM studenci WHERE id=?";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.execute();
            addToEventLog("DELETE studenci", id);
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
            if(student.czynsz.get() != 0){
//                System.out.println("Czynsz != 0");
                stmt.setInt(9, (getCzynsze().indexOf(student.czynsz.get())+1));
            }else {
                stmt.setNull(9, Types.INTEGER);
            }
            stmt.setInt(10, (getStolowki().indexOf(student.rodzajStolowki.get())+1));
            stmt.execute();
            addToEventLog("INSERT studenci", student.id.getValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void modifyStudent(Student student){
        String query = "UPDATE studenci SET Nazwisko = ?, Imie = ?, DataUrodzenia = ?, NrTelefonu = ?, AdresEmail = ?, AdresZamieszkania = ?, kierunekstudiow_id = ?, rokstudiow_id = ?, czynsz_id = ?, stolowka_id = ? WHERE id = ?;";
        PreparedStatement stmt = null;
        System.out.println(student.dataUrodzenia.get());
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
            if(student.czynsz.get() != 0){
//                System.out.println("Czynsz != 0");
                stmt.setInt(9, (getCzynsze().indexOf(student.czynsz.get())+1));
            }else {
                stmt.setNull(9, Types.INTEGER);
            }
            stmt.setInt(10, (getStolowki().indexOf(student.rodzajStolowki.get())+1));
//            System.out.println("Data: "+student.dataUrodzenia.get().toString());
//            System.out.println("Kierunek id:" + (getSymbols().indexOf(student.kierunek.get())+1));
//            System.out.println("Rok studiow id:" + (getYears().indexOf(student.rokStudiow.get())+1));
//            System.out.println("Czynsz id:" + (getCzynsze().indexOf(student.czynsz.get())+1));
//            System.out.println("Stolowka id:" + (getStolowki().indexOf(student.rodzajStolowki.get())+1));
            stmt.setInt(11, student.id.getValue());
            stmt.execute();
            addToEventLog("UPDATE studenci", student.id.getValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Student> getStudentsBySelected(OptionDataPair dataPair){
        String queryName = "select studenci.id, studenci.nazwisko, studenci.imie, studenci.dataurodzenia as data_urodzenia, studenci.nrtelefonu as nr_telefonu, studenci.adresemail as adres_email, studenci.adreszamieszkania as adres_zamieszkania, kierunekstudiow.symbolkierunku as symbol_kierunku, rokstudiow.rokstudiow as rok_studiow, czynsz.wysokoscczynszu as wysokosc_czynszu, stolowka.rodzaj as rodzaj_stolowki from studenci left join kierunekstudiow on studenci.kierunekstudiow_id = kierunekstudiow.id left join rokstudiow on studenci.rokstudiow_id = rokstudiow.id left join czynsz on studenci.czynsz_id = czynsz.id left join stolowka on studenci.stolowka_id = stolowka.id where imie like ? order by studenci.id asc;";
        String querySurname = "select studenci.id, studenci.nazwisko, studenci.imie, studenci.dataurodzenia as data_urodzenia, studenci.nrtelefonu as nr_telefonu, studenci.adresemail as adres_email, studenci.adreszamieszkania as adres_zamieszkania, kierunekstudiow.symbolkierunku as symbol_kierunku, rokstudiow.rokstudiow as rok_studiow, czynsz.wysokoscczynszu as wysokosc_czynszu, stolowka.rodzaj as rodzaj_stolowki from studenci left join kierunekstudiow on studenci.kierunekstudiow_id = kierunekstudiow.id left join rokstudiow on studenci.rokstudiow_id = rokstudiow.id left join czynsz on studenci.czynsz_id = czynsz.id left join stolowka on studenci.stolowka_id = stolowka.id where nazwisko like ? order by studenci.id asc;";
        String queryAddress = "select studenci.id, studenci.nazwisko, studenci.imie, studenci.dataurodzenia as data_urodzenia, studenci.nrtelefonu as nr_telefonu, studenci.adresemail as adres_email, studenci.adreszamieszkania as adres_zamieszkania, kierunekstudiow.symbolkierunku as symbol_kierunku, rokstudiow.rokstudiow as rok_studiow, czynsz.wysokoscczynszu as wysokosc_czynszu, stolowka.rodzaj as rodzaj_stolowki from studenci left join kierunekstudiow on studenci.kierunekstudiow_id = kierunekstudiow.id left join rokstudiow on studenci.rokstudiow_id = rokstudiow.id left join czynsz on studenci.czynsz_id = czynsz.id left join stolowka on studenci.stolowka_id = stolowka.id where adreszamieszkania like ? order by studenci.id asc;";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ObservableList<Student> data = FXCollections.observableArrayList();
        try {
            String like = "%"+dataPair.getData()+"%";
            switch (dataPair.getOption()){
                case "Imie":
                    stmt = conn.prepareStatement(queryName);
                    stmt.setString(1, like);
                    break;
                case "Nazwisko":
                    stmt = conn.prepareStatement(querySurname);
                    stmt.setString(1, like);
                    break;
                case "Adres":
                    stmt = conn.prepareStatement(queryAddress);
                    stmt.setString(1, like);
                    break;
            }
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignResultsetToStudent(rs);
    }
    //End manage students
    //Start manage accommodation
    private ObservableList<Accommodation> assignResultsetToAccomm(ResultSet rs){
        ObservableList<Accommodation> data = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                Accommodation accommodation = new Accommodation();
                accommodation.id_accomm.set(rs.getInt("numer_zakwaterowania"));
                accommodation.floor.set(rs.getInt("pietro"));
                accommodation.room_nr.set(rs.getInt("numer_pokoju"));
                accommodation.room_size.set(rs.getInt("wielkosc_pokoju"));
                accommodation.imie.set(rs.getString("imie"));
                accommodation.nazwisko.set(rs.getString("nazwisko"));
                data.add(accommodation);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return data;
    }

    public ObservableList<Accommodation> getAccommodation(){
        String query = "select zakwaterowanie.id as numer_zakwaterowania, pokoje.pietro, pokoje.numerpokoju as numer_pokoju, pokoje.wielkoscpokoju as wielkosc_pokoju, studenci.imie, studenci.nazwisko from zakwaterowanie left join pokoje on zakwaterowanie.pokoje_id = pokoje.id left join studenci on zakwaterowanie.studenci_id = studenci.id order by zakwaterowanie.id asc";
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return assignResultsetToAccomm(rs);
    }

    public ObservableList<Accommodation> getAccommodationByFloor(int floor){
        String query = "select zakwaterowanie.id as numer_zakwaterowania, pokoje.pietro, pokoje.numerpokoju as numer_pokoju, pokoje.wielkoscpokoju as wielkosc_pokoju, studenci.imie, studenci.nazwisko from zakwaterowanie left join pokoje on zakwaterowanie.pokoje_id = pokoje.id left join studenci on zakwaterowanie.studenci_id = studenci.id where pietro = ? order by zakwaterowanie.id asc;";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, floor);
            rs = stmt.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return assignResultsetToAccomm(rs);
    }

    public ObservableList<Accommodation> getAccommodationByRoom(int room){
        String query = "select zakwaterowanie.id as numer_zakwaterowania, pokoje.pietro, pokoje.numerpokoju as numer_pokoju, pokoje.wielkoscpokoju as wielkosc_pokoju, studenci.imie, studenci.nazwisko from zakwaterowanie left join pokoje on zakwaterowanie.pokoje_id = pokoje.id left join studenci on zakwaterowanie.studenci_id = studenci.id where numerpokoju = ? order by zakwaterowanie.id asc;";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, room);
            rs = stmt.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return assignResultsetToAccomm(rs);
    }

    public void addAccomm(int room, int student){
        String query = "INSERT INTO `zakwaterowanie`(`Pokoje_id`, `Studenci_id`) VALUES (?,?)";
        PreparedStatement stmt = null;
        try {
            int roomId = getRoomIdByNr(room);
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, roomId);
            stmt.setInt(2, student);
            System.out.println("SID: "+student+" RID: "+roomId);
            stmt.execute();
            addToEventLog("INSERT zakwaterowanie", student);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifyAccomm(int room, int student, int id){
        String query = "update zakwaterowanie set pokoje_id = ?, studenci_id = ? where id = ?;";
        PreparedStatement stmt = null;
        try {
            int roomId = getRoomIdByNr(room);
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, roomId);
            stmt.setInt(2, student);
            stmt.setInt(3, id);
            stmt.execute();
            addToEventLog("UPDATE zakwaterowanie", student);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteAccommById(int id){
        String query = "DELETE FROM zakwaterowanie WHERE id=?";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.execute();
            addToEventLog("DELETE zakwaterowanie", id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Integer> getRooms(){
        String query = "SELECT NumerPokoju FROM pokoje";
        List<Integer> options = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()){
                options.add(rs.getInt("NumerPokoju"));
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return options;
    }

    private int getRoomIdByNr(int nr){
        String query = "SELECT id FROM pokoje WHERE NumerPokoju = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, nr);
            rs = stmt.executeQuery();
            rs.first();
            return rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Room> getNotFullRooms(){
        String query = "select pokoje.id as pokoje_id, numerpokoju as numer_pokoju, COUNT(zakwaterowanie.pokoje_id) as ilosc_osob, pokoje.wielkoscpokoju from pokoje left outer join zakwaterowanie on pokoje.id = zakwaterowanie.pokoje_id group by zakwaterowanie.pokoje_id having COUNT(zakwaterowanie.pokoje_id) < pokoje.wielkoscpokoju union select pokoje.id as pokoje_id, numerpokoju as numer_pokoju, COUNT(zakwaterowanie.pokoje_id) as ilosc_osob, pokoje.wielkoscpokoju from pokoje right outer join zakwaterowanie on pokoje.id = zakwaterowanie.pokoje_id group by zakwaterowanie.pokoje_id having COUNT(zakwaterowanie.pokoje_id) < pokoje.wielkoscpokoju order by pokoje_id;";
        List<Room> rooms = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()){
                rooms.add(new Room(
                        rs.getInt("numer_pokoju"),
                        rs.getInt("ilosc_osob"),
                        rs.getInt("wielkoscpokoju")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return rooms;
    }

    public HashMap<Integer, String> getStudentsWithNullCzynsz(){
        String query = "SELECT id, Imie, Nazwisko FROM studenci";
        HashMap<Integer, String> idStudent = new HashMap<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()){
                String x = rs.getString("Imie") +" "+rs.getString("Nazwisko");
                idStudent.put(rs.getInt("id"), x);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return idStudent;
    }

    public int getStudentId(String name, String surname){
        String query = "SELECT id FROM studenci WHERE Imie = ? AND Nazwisko = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, surname);
            rs = stmt.executeQuery();
            rs.first();
            return rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    //End manage accommodation
    //Start payment history
    public ObservableList<PaymentHistory> getPaymentHistory(){
        String query = "select historiawplat.id as numer_wplaty, studenci.id as id_studenta, studenci.imie, studenci.nazwisko, historiawplat.data, historiawplat.kwotawplaty as Kwota_wplaty from historiawplat left join studenci on historiawplat.studenci_id = studenci.id order by historiawplat.id asc;";
        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<PaymentHistory> data = FXCollections.observableArrayList();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                PaymentHistory paymentHistory = new PaymentHistory();
                paymentHistory.nr_wplaty.set(rs.getInt("numer_wplaty"));
                paymentHistory.id_studenta.set(rs.getInt("id_studenta"));
                paymentHistory.imie.set(rs.getString("imie"));
                paymentHistory.nazwisko.set(rs.getString("nazwisko"));
                paymentHistory.data.set(rs.getDate("data"));
                paymentHistory.kwota_wplaty.set(rs.getInt("Kwota_wplaty"));
                data.add(paymentHistory);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return data;
    }
    public ObservableList<PaymentHistory> getPaymentHistoryByMonth(int monthId){
        String query = "select historiawplat.id as numer_wplaty, studenci.id as id_studenta, studenci.imie, studenci.nazwisko, historiawplat.data, historiawplat.kwotawplaty as Kwota_wplaty from historiawplat left join studenci on historiawplat.studenci_id = studenci.id where data like ? order by historiawplat.id asc; ";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ObservableList<PaymentHistory> data = FXCollections.observableArrayList();
        try {
            stmt = conn.prepareStatement(query);
            String like;
            if(monthId < 10){// dopis 0 do like
                like = "2020-0"+monthId+"-%";
            }else {
                like = "2020-"+monthId+"-%";
            }
            stmt.setString(1, like);
            rs = stmt.executeQuery();
            while (rs.next()) {
                PaymentHistory paymentHistory = new PaymentHistory();
                paymentHistory.nr_wplaty.set(rs.getInt("numer_wplaty"));
                paymentHistory.id_studenta.set(rs.getInt("id_studenta"));
                paymentHistory.imie.set(rs.getString("imie"));
                paymentHistory.nazwisko.set(rs.getString("nazwisko"));
                paymentHistory.data.set(rs.getDate("data"));
                paymentHistory.kwota_wplaty.set(rs.getInt("Kwota_wplaty"));
                data.add(paymentHistory);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return data;
    }

    public ObservableList<PaymentHistory> getNotPaidByMonth(int monthId){
        String query = "select '' as numer_wplaty, studenci.id as id_studenta, studenci.imie, studenci.nazwisko, null as data, '' as Kwota_wplaty from studenci where id not in (select studenci.id from historiawplat left join studenci on historiawplat.studenci_id = studenci.id where data like ?) AND studenci.czynsz_id is not null";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ObservableList<PaymentHistory> data = FXCollections.observableArrayList();
        try {
            stmt = conn.prepareStatement(query);
            String like;
            if(monthId < 10){// dopis 0 do like
                like = "2020-0"+monthId+"-%";
            }else {
                like = "2020-"+monthId+"-%";
            }
            stmt.setString(1, like);
            rs = stmt.executeQuery();
            while (rs.next()) {
                PaymentHistory paymentHistory = new PaymentHistory();
                paymentHistory.nr_wplaty.set(rs.getInt("numer_wplaty"));
                paymentHistory.id_studenta.set(rs.getInt("id_studenta"));
                paymentHistory.imie.set(rs.getString("imie"));
                paymentHistory.nazwisko.set(rs.getString("nazwisko"));
                paymentHistory.data.set(rs.getDate("data"));
                paymentHistory.kwota_wplaty.set(rs.getInt("Kwota_wplaty"));
                data.add(paymentHistory);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return data;
    }
    //End payment history
    //Start event log
    private void addToEventLog(String mess, int targetId){
        String user = Context.getInstance().getUser().getLogin();
        String query = "INSERT INTO `dziennikzdarzen`(`uzytkownik`, `Data`, `Akcja`, `Cel`) VALUES (?,NOW(),?,?)";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, user);
            stmt.setString(2, mess);
            stmt.setInt(3, targetId);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<EventLog> getEventLog(){
        String query = "select id, uzytkownik, Data, Akcja,  Cel from dziennikzdarzen;";
        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<EventLog> data = FXCollections.observableArrayList();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                EventLog eventLog = new EventLog();
                eventLog.id.set(rs.getInt("id"));
                eventLog.user.set(rs.getString("uzytkownik"));
                eventLog.data.set(rs.getDate("Data"));
                eventLog.action.set(rs.getString("Akcja"));
                eventLog.target.set(rs.getInt("Cel"));
                data.add(eventLog);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return data;
    }
    public ObservableList<EventLog> getEventLogByUser(String user){
        String query = "select id, uzytkownik, Data, Akcja,  Cel from dziennikzdarzen WHERE BINARY uzytkownik =?;";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ObservableList<EventLog> data = FXCollections.observableArrayList();
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, user);
            rs = stmt.executeQuery();
            while (rs.next()) {
                EventLog eventLog = new EventLog();
                eventLog.id.set(rs.getInt("id"));
                eventLog.user.set(rs.getString("uzytkownik"));
                eventLog.data.set(rs.getDate("Data"));
                eventLog.action.set(rs.getString("Akcja"));
                eventLog.target.set(rs.getInt("Cel"));
                data.add(eventLog);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return data;
    }
    //End event log

}
