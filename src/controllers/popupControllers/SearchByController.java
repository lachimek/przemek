package controllers.popupControllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Context;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchByController implements Initializable {
    public Button btn;
    public TextField inputTF;
    public ComboBox<String> typeComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeComboBox.setItems(FXCollections.observableArrayList("Imie", "Nazwisko", "Adres"));
        typeComboBox.getSelectionModel().selectFirst();
    }


    public void search() {
        Context.getInstance().getOptionDataPair().setPair(typeComboBox.getSelectionModel().getSelectedItem(), inputTF.getText());
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
}
