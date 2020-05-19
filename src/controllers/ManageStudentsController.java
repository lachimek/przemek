package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageStudentsController implements Initializable {


    public AnchorPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void backToMainMenu() throws IOException {
        VBox pane = FXMLLoader.load(getClass().getResource("../views/menu_glowne.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
