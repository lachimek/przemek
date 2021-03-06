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

public class SelectYearController implements Initializable {
    public ComboBox comboYear;
    public Button btn;
    private DbHandler db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = Context.getInstance().getDbHandler();
        List<Integer> options = db.getYears();
        comboYear.setItems(FXCollections.observableArrayList(options));
    }

    public void selectYear() {
        Context.getInstance().selectedYear = Integer.parseInt(comboYear.getValue().toString());
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
}
