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

public class SelectCzynszController implements Initializable {
    public ComboBox comboCzynsz;
    public Button btn;
    private DbHandler db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = Context.getInstance().getDbHandler();
        List<Integer> options = db.getCzynsze();
        comboCzynsz.setItems(FXCollections.observableArrayList(options));
    }

    public void selectCzynsz() {
        Context.getInstance().selectedCzynsz = Integer.parseInt(comboCzynsz.getValue().toString());
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
}
