package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.util.Optional;
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
        try {
            table.setItems(db.getAllStudents());
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void showByCourse(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/popups/selectProfilePopup.fxml"));
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

    public void showByYear(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/popups/selectYearPopup.fxml"));
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.showAndWait();
            //System.out.println("MSC: "+Context.getInstance().selectedSymbol);
            table.setItems(db.getStudentsByYear(Context.getInstance().selectedYear));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showByCzynsz() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/popups/selectCzynszPopup.fxml"));
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.showAndWait();
            //System.out.println("MSC: "+Context.getInstance().selectedSymbol);
            table.setItems(db.getStudentsByCzynsz(Context.getInstance().selectedCzynsz));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showByStolowka() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/popups/selectStolowkaPopup.fxml"));
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.showAndWait();
            //System.out.println("MSC: "+Context.getInstance().selectedStolowka);
            table.setItems(db.getStudentsByStolowka(Context.getInstance().selectedStolowka));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteSelected(){
        Student s = (Student) table.getSelectionModel().getSelectedItem();
        int selectedId = 0;
        try {
            selectedId = s.id.getValue();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdź usuwanie");
            alert.setHeaderText(null);
            alert.setContentText("Czy chcesz usunąć tego studenta?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (db.deleteStudentById(selectedId) && selectedId > 0) {
                    Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                    alertInfo.setTitle("Sukces");
                    alertInfo.setHeaderText(null);
                    alertInfo.setContentText("Usuwanie przebiegło pomyślnie");
                    alertInfo.initOwner(rootPane.getScene().getWindow());
                    alertInfo.showAndWait();
                    showStudents();
                } else {
                    Alert alertErr1 = new Alert(Alert.AlertType.ERROR);
                    alertErr1.setTitle("Błąd");
                    alertErr1.setHeaderText(null);
                    alertErr1.setContentText("Wystąpił nieoczekiwany błąd");
                    alertErr1.initOwner(rootPane.getScene().getWindow());
                    alertErr1.showAndWait();
                }
            }
        }catch (NullPointerException e){
            Alert alertErr2 = new Alert(Alert.AlertType.ERROR);
            alertErr2.setTitle("Błąd");
            alertErr2.setHeaderText(null);
            alertErr2.setContentText("Proszę wybrać studenta z listy");
            alertErr2.initOwner(rootPane.getScene().getWindow());
            alertErr2.showAndWait();
        }
    }

    public void addNewStudent() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/popups/addStudentPopup.fxml"));
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.showAndWait();
            showStudents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modifyStudent(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/popups/modifyStudentPopup.fxml"));
        Student s = (Student) table.getSelectionModel().getSelectedItem();
        Parent layout;
        try {
            Context.getInstance().setStudent(s);
            layout = loader.load();
            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.showAndWait();
            showStudents();
        } catch (IOException e) {
            Alert alertErr2 = new Alert(Alert.AlertType.ERROR);
            alertErr2.setTitle("Błąd");
            alertErr2.setHeaderText(null);
            alertErr2.setContentText("Proszę wybrać studenta z listy");
            alertErr2.initOwner(rootPane.getScene().getWindow());
            alertErr2.showAndWait();
            e.printStackTrace();
        }
    }

    public void searchStudentBy(ActionEvent actionEvent) {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/popups/searchByPopup.fxml"));
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.showAndWait();
            //System.out.println("MSC: "+Context.getInstance().selectedStolowka);
            table.setItems(db.getStudentsBySelected(Context.getInstance().getOptionDataPair()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backToMainMenu() throws IOException {
        VBox pane = FXMLLoader.load(getClass().getClassLoader().getResource("views/menu_glowne.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
