package controllers.popupControllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import main.Context;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectFloorController implements Initializable {
    public Button btn;
    public ComboBox comboPietro;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboPietro.setItems(FXCollections.observableArrayList(1, 2, 3));
        comboPietro.getSelectionModel().selectFirst();
    }

    public void selectFloor() {
        Context.getInstance().selectedFloor = (int) comboPietro.getSelectionModel().getSelectedItem();
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
}
