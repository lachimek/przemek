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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ModifyStudentController implements Initializable {
    public Button btn;
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

    private DbHandler db;
    private Student student;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = Context.getInstance().getDbHandler();
        student = Context.getInstance().getStudent();
        comboProfile.setItems(FXCollections.observableArrayList(db.getSymbols()));
        comboYear.setItems(FXCollections.observableArrayList(db.getYears()));
        comboCzynsz.setItems(FXCollections.observableArrayList(db.getCzynsze()));
        comboStolowka.setItems(FXCollections.observableArrayList(db.getStolowki()));
        surname.setText(student.nazwisko.get());
        name.setText(student.imie.get());
        Date d = (Date) student.dataUrodzenia.get();
        birthDate.setValue(d.toLocalDate());
        phoneNr.setText(String.valueOf(student.nrTelefonu.get()));
        email.setText(student.email.get());
        address.setText(student.adres.get());
        comboProfile.setValue(student.kierunek.get());
        comboYear.setValue(student.rokStudiow.get());
        comboCzynsz.setValue(student.czynsz.get());
        comboStolowka.setValue(student.rodzajStolowki.get());
    }

    public void saveStudent() {
        Student newStudent = new Student();
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
            newStudent.id.set(student.id.get());
            newStudent.nazwisko.set(surname.getText());
            newStudent.imie.set(name.getText());
            Date date = Date.valueOf(birthDate.getValue());
            student.dataUrodzenia.set(date);
            String nr = phoneNr.getText().replaceAll("\\D+","");
            newStudent.nrTelefonu.set(Integer.parseInt(nr));
            newStudent.email.set(email.getText());
            newStudent.adres.set(address.getText());
            newStudent.nazwisko.set(surname.getText());
            newStudent.kierunek.set(comboProfile.getValue().toString());
            newStudent.rokStudiow.set(Integer.parseInt(comboYear.getValue().toString()));
            newStudent.czynsz.set(Integer.parseInt(comboCzynsz.getValue().toString()));
            newStudent.rodzajStolowki.set(comboStolowka.getValue().toString());
            db.modifyStudent(newStudent);
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        }
    }
}
