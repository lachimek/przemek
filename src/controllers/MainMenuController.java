package controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.Context;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    public Button manageStudents;
    public Button manageAccomm;
    public Button showEventLog;
    public Button showPaymentHistory;
    public Button logOutButton;
    public Label loggedInLabel;
    public AnchorPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loggedInLabel.setText("Jesteś zalogowany jako: "+ Context.getInstance().getUser().getLogin());
    }

    public void logout() throws IOException {
        Context.getInstance().getUser().logout();
        VBox pane = FXMLLoader.load(getClass().getResource("../views/logowanie.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    public void openManageStudents() throws IOException {
        VBox pane = FXMLLoader.load(getClass().getResource("../views/zarzadzanie_studenci.fxml"));
        System.out.println(pane.getPrefWidth()+" "+pane.getPrefHeight());
        rootPane.getChildren().setAll(pane);
    }

    public void openManageAccomm() throws IOException {
        VBox pane = FXMLLoader.load(getClass().getResource("../views/zarzadzanie_zakwaterowanie.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    public void openEventLog() throws IOException {
        VBox pane = FXMLLoader.load(getClass().getResource("../views/dziennik_zdarzen.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    public void openPaymentHistory() throws IOException {
        VBox pane = FXMLLoader.load(getClass().getResource("../views/historia_wplat.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
