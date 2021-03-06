package controllers.popupControllers;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import main.Context;
import main.DbHandler;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SelectProfileController implements Initializable {
    public ComboBox comboSymbols;
    public Button btn;
    private DbHandler db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = Context.getInstance().getDbHandler();
        List<String> options = db.getSymbols();
        comboSymbols.setItems(FXCollections.observableArrayList(options));
    }

    public void selectSymbol(){
        Context.getInstance().selectedSymbol = comboSymbols.getValue().toString();
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
}
