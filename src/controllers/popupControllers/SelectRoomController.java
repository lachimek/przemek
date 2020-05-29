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

public class SelectRoomController implements Initializable {
    public Button btn;
    public ComboBox<Integer> comboPokoje;

    private DbHandler db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = Context.getInstance().getDbHandler();
        List<Integer> options = db.getRooms();
        comboPokoje.setItems(FXCollections.observableArrayList(options));
    }

    public void selectRoom() {
        Context.getInstance().selectedRoom = comboPokoje.getValue();
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
}
