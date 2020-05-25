package controllers.popupControllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import main.Context;
import main.DbHandler;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SelectStolowkaController implements Initializable {
    public ComboBox comboStolowki;
    public Button btn;
    private DbHandler db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = Context.getInstance().getDbHandler();
        List<String> options = db.getStolowki();
        comboStolowki.setItems(FXCollections.observableArrayList(options));
    }

    public void selectStolowka() {
        Context.getInstance().selectedStolowka = comboStolowki.getValue().toString();
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
}
