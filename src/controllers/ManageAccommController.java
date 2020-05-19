package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ManageAccommController {

    public AnchorPane rootPane;

    public void backToMainMenu() throws IOException {
        VBox pane = FXMLLoader.load(getClass().getResource("../views/menu_glowne.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
