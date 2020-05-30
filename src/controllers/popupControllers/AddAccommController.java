package controllers.popupControllers;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import main.Context;
import main.DbHandler;
import main.Room;

import java.net.URL;
import java.util.*;

public class AddAccommController implements Initializable {
    public Button btn;
    public ComboBox<Integer> comboPokoje;
    public ComboBox<String> comboStudenci;

    private HashMap<Integer, String> studentsWithNullCzynsz;
    private DbHandler db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = Context.getInstance().getDbHandler();
        studentsWithNullCzynsz = db.getStudentsWithNullCzynsz();
        List<String> students = new ArrayList<>();
        List<Integer> rooms = new ArrayList<>();
        for (int key: studentsWithNullCzynsz.keySet()) {
            students.add(studentsWithNullCzynsz.get(key));
        }
        for (Room r: db.getNotFullRooms()){
            rooms.add(r.getRoomNr());
        }
        comboStudenci.setItems(FXCollections.observableArrayList(students));
        comboPokoje.setItems(FXCollections.observableArrayList(rooms));
    }

    public void addAccomm() {
        if(comboPokoje.getValue() == null || comboStudenci.getValue() == null){
            Alert alertErr2 = new Alert(Alert.AlertType.ERROR);
            alertErr2.setTitle("Błąd");
            alertErr2.setHeaderText(null);
            alertErr2.setContentText("Prosze uzupełnić puste pola");
            alertErr2.initOwner(btn.getScene().getWindow());
            alertErr2.showAndWait();
        }else {
            int room = comboPokoje.getSelectionModel().getSelectedItem();
            int studId = getKey(studentsWithNullCzynsz, comboStudenci.getSelectionModel().getSelectedItem());

            db.addAccomm(room, studId);
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
