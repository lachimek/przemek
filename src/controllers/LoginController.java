package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.Context;

import java.io.IOException;

public class LoginController {
    public Button loginBtn;
    public TextField passTF;
    public TextField loginTF;
    public AnchorPane rootPane;

    public void logIn() throws IOException {
        String testlogin = "admin";
        String testpass = "admin1";

        if(Context.getInstance().getDbHandler().loginUser(loginTF.getText(), passTF.getText())){
            System.out.println("success");
            Context.getInstance().getUser().logIn(loginTF.getText(), passTF.getText());
            VBox pane = FXMLLoader.load(getClass().getResource("../views/menu_glowne.fxml"));
            rootPane.getChildren().setAll(pane);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd logowania");
            alert.setHeaderText(null);
            alert.setContentText("Błędne dane logowania");
            alert.initOwner(rootPane.getScene().getWindow());
            alert.showAndWait();
            System.out.println("invalid credentials");
        }
    }
}
