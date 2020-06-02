package controllers.popupControllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Context;
import main.DbHandler;
import main.PaymentHistory;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class ModifyPaymentController implements Initializable {
    public Button btn;
    public TextField amount;
    public ComboBox<String> comboStudents;
    public DatePicker date;


    private PaymentHistory ph;
    private DbHandler db;
    private HashMap<Integer, String> studentsMap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = Context.getInstance().getDbHandler();
        ph = Context.getInstance().getPaymentHistory();
        List<String> students = new ArrayList<>();
        students.add(ph.imie.get()+" "+ph.nazwisko.get());
        studentsMap = db.getAllStudentsMap();
        for (int key: studentsMap.keySet()) {
            students.add(studentsMap.get(key));
        }
        comboStudents.setItems(FXCollections.observableArrayList(students));

        comboStudents.setValue(students.get(0));
        Date dateConversion = (Date) ph.data.get();
        date.setValue(dateConversion.toLocalDate());
        amount.setText(String.valueOf(ph.kwota_wplaty.get()));
    }

    public void modify() {
        if(amount.getText().isEmpty() || comboStudents.getValue() == null || date.getValue() == null){
            Alert alertErr2 = new Alert(Alert.AlertType.ERROR);
            alertErr2.setTitle("Błąd");
            alertErr2.setHeaderText(null);
            alertErr2.setContentText("Prosze uzupełnić puste pola");
            alertErr2.initOwner(btn.getScene().getWindow());
            alertErr2.showAndWait();
        }else {
            int studId = 0;
            if(!comboStudents.getSelectionModel().getSelectedItem().equals(ph.imie.get() + " " + ph.nazwisko.get())) {
                studId = getKey(studentsMap, comboStudents.getSelectionModel().getSelectedItem());
            }else {
                studId = db.getStudentId(ph.imie.get(), ph.nazwisko.get());
            }
            Date d = Date.valueOf(date.getValue());
            String amountSanitized = amount.getText().replaceAll("\\D+","");
            db.modifyPayment(studId, d, Integer.parseInt(amountSanitized), ph.nr_wplaty.get());
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        }
    }

    private <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
