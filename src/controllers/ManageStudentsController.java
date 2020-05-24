package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.Context;
import main.DbHandler;
import main.Student;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ManageStudentsController implements Initializable {
    private DbHandler db;

    public AnchorPane rootPane;
    public TableView table;

    public TableColumn colId;
    public TableColumn colNazwisko;
    public TableColumn colImie;
    public TableColumn colData;
    public TableColumn colTel;
    public TableColumn colMail;
    public TableColumn colAdres;
    public TableColumn colKierunek;
    public TableColumn colRok;
    public TableColumn colCzynsz;
    public TableColumn colStol;

    public String selectedSymbol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = Context.getInstance().getDbHandler();
        colId.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
        colNazwisko.setCellValueFactory(new PropertyValueFactory<Student, String>("nazwisko"));
        colImie.setCellValueFactory(new PropertyValueFactory<Student, String>("imie"));
        colData.setCellValueFactory(new PropertyValueFactory<Student, Date>("dataUrodzenia"));
        colTel.setCellValueFactory(new PropertyValueFactory<Student, Integer>("nrTelefonu"));
        colMail.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
        colAdres.setCellValueFactory(new PropertyValueFactory<Student, String>("adres"));
        colKierunek.setCellValueFactory(new PropertyValueFactory<Student, String>("kierunek"));
        colRok.setCellValueFactory(new PropertyValueFactory<Student, Integer>("rokStudiow"));
        colCzynsz.setCellValueFactory(new PropertyValueFactory<Student, Integer>("czynsz"));
        colStol.setCellValueFactory(new PropertyValueFactory<Student, String>("rodzajStolowki"));
        showStudents();
    }

    public void showStudents() {
        ResultSet rs = db.getAllStudents();
        try {
            ObservableList<Student> data = FXCollections.observableArrayList();
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
            table.setItems(data);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void showByCourse(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../views/selectProfilePopup.fxml"));
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.showAndWait();
            //System.out.println("MSC: "+Context.getInstance().selectedSymbol);
            table.setItems(db.getStudentsByProfile(Context.getInstance().selectedSymbol));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteSelected(){
        Student s = (Student) table.getSelectionModel().getSelectedItem();
        int selectedId = 0;
        try {
            selectedId = s.id.getValue();
            if(db.deleteStudentById(selectedId) && selectedId>0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sukces");
                alert.setHeaderText(null);
                alert.setContentText("Usuwanie przebiegło pomyślnie");
                alert.initOwner(rootPane.getScene().getWindow());
                alert.showAndWait();
                showStudents();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText(null);
                alert.setContentText("Wystąpił nieoczekiwany błąd");
                alert.initOwner(rootPane.getScene().getWindow());
                alert.showAndWait();
            }
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("Proszę wybrać studenta z listy");
            alert.initOwner(rootPane.getScene().getWindow());
            alert.showAndWait();
        }
    }

    public void backToMainMenu() throws IOException {
        VBox pane = FXMLLoader.load(getClass().getResource("../views/menu_glowne.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
