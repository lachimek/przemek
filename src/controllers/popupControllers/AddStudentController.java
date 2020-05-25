package controllers.popupControllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Context;
import main.DbHandler;
import main.Student;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class AddStudentController implements Initializable {
    public TextField surname;
    public TextField name;
    public DatePicker birthDate;
    public TextField phoneNr;
    public TextField email;
    public TextField address;
    public ComboBox comboProfile;
    public ComboBox comboYear;
    public ComboBox comboCzynsz;
    public ComboBox comboStolowka;
    public Button btn;
    private DbHandler db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = Context.getInstance().getDbHandler();
        comboProfile.setItems(FXCollections.observableArrayList(db.getSymbols()));
        comboYear.setItems(FXCollections.observableArrayList(db.getYears()));
        comboCzynsz.setItems(FXCollections.observableArrayList(db.getCzynsze()));
        comboStolowka.setItems(FXCollections.observableArrayList(db.getStolowki()));
    }

    public void addStudent() {
        Student student = new Student();
        if(surname.getText().isEmpty() || name.getText().isEmpty() ||
                birthDate.getValue() == null || phoneNr.getText().isEmpty() ||
                email.getText().isEmpty() || address.getText().isEmpty() ||
                comboProfile.getValue() == null || comboProfile.getValue() == null ||
                comboCzynsz.getValue() == null || comboStolowka.getValue() == null){
            Alert alertErr2 = new Alert(Alert.AlertType.ERROR);
            alertErr2.setTitle("Błąd");
            alertErr2.setHeaderText(null);
            alertErr2.setContentText("Prosze uzupełnić puste pola");
            alertErr2.initOwner(btn.getScene().getWindow());
            alertErr2.showAndWait();
        }else {
            student.nazwisko.set(surname.getText());
            student.imie.set(name.getText());
            Date date = Date.valueOf(birthDate.getValue());
            student.dataUrodzenia.set(date);
            String nr = phoneNr.getText().replaceAll("\\D+","");
            student.nrTelefonu.set(Integer.parseInt(nr));
            student.email.set(email.getText());
            student.adres.set(address.getText());
            student.nazwisko.set(surname.getText());
            student.kierunek.set(comboProfile.getValue().toString());
            student.rokStudiow.set(Integer.parseInt(comboYear.getValue().toString()));
            student.czynsz.set(Integer.parseInt(comboCzynsz.getValue().toString()));
            student.rodzajStolowki.set(comboStolowka.getValue().toString());
            db.addNewStudent(student);
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        }
    }
}
