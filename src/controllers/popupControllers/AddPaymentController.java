package controllers.popupControllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Context;
import main.DbHandler;

import java.net.URL;
import java.sql.Date;
import java.util.*;

public class AddPaymentController implements Initializable {
    public TextField amount;
    public ComboBox<String> comboStudents;
    public DatePicker date;
    public Button btn;

    private DbHandler db;
    private HashMap<Integer, String> studentsMap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = Context.getInstance().getDbHandler();
        studentsMap = db.getAllStudentsMap();
        List<String> students = new ArrayList<>();
        for (int key: studentsMap.keySet()) {
            students.add(studentsMap.get(key));
        }
        comboStudents.setItems(FXCollections.observableArrayList(students));
    }

    public void addPayment() {
        if(amount.getText().isEmpty() || comboStudents.getValue() == null || date.getValue() == null){
            Alert alertErr2 = new Alert(Alert.AlertType.ERROR);
            alertErr2.setTitle("Błąd");
            alertErr2.setHeaderText(null);
            alertErr2.setContentText("Prosze uzupełnić puste pola");
            alertErr2.initOwner(btn.getScene().getWindow());
            alertErr2.showAndWait();
        }else {
            Date d = Date.valueOf(date.getValue());
            int studId = getKey(studentsMap, comboStudents.getSelectionModel().getSelectedItem());
            String amountSanitized = amount.getText().replaceAll("\\D+","");
            db.addNewPayment(studId, d, Integer.parseInt(amountSanitized));
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
