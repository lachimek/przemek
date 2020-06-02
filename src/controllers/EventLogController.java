package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Context;
import main.DbHandler;
import main.EventLog;
import main.PaymentHistory;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class EventLogController implements Initializable {
    public AnchorPane rootPane;
    public TableView table;
    public TableColumn colId;
    public TableColumn colUser;
    public TableColumn colData;
    public TableColumn colAkcja;
    public TableColumn colCel;

    private DbHandler db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = Context.getInstance().getDbHandler();
        colId.setCellValueFactory(new PropertyValueFactory<EventLog, Integer>("id"));
        colUser.setCellValueFactory(new PropertyValueFactory<EventLog, String>("user"));
        colData.setCellValueFactory(new PropertyValueFactory<EventLog, Date>("data"));
        colAkcja.setCellValueFactory(new PropertyValueFactory<EventLog, String>("action"));
        colCel.setCellValueFactory(new PropertyValueFactory<EventLog, Integer>("target"));

        showEventLog();
    }

    public void showEventLog() {
        table.setItems(db.getEventLog());
    }

    public void showForUser() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/popups/selectUserPopup.fxml"));
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.showAndWait();
            table.setItems(db.getEventLogByUser(Context.getInstance().selectedUser));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backToMainMenu() throws IOException {
        VBox pane = FXMLLoader.load(getClass().getClassLoader().getResource("views/menu_glowne.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
