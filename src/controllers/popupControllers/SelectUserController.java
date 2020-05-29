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

public class SelectUserController implements Initializable {

    public Button btn;
    public ComboBox<String> comboUsers;
    private DbHandler db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = Context.getInstance().getDbHandler();
        List<String> users = db.getUsers();
        comboUsers.setItems(FXCollections.observableArrayList(users));
    }

    public void selectUser() {
        Context.getInstance().selectedUser = comboUsers.getValue();
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
}
