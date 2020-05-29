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
import main.Accommodation;
import main.Context;
import main.DbHandler;
import main.Student;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ManageAccommController implements Initializable {

    public AnchorPane rootPane;
    public TableView table;
    public TableColumn colIdZakw;
    public TableColumn colPietro;
    public TableColumn colNrPok;
    public TableColumn colWielkPok;
    public TableColumn colImie;
    public TableColumn colNazwisko;

    private DbHandler db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = Context.getInstance().getDbHandler();
        colIdZakw.setCellValueFactory(new PropertyValueFactory<Accommodation, Integer>("id_accomm"));
        colPietro.setCellValueFactory(new PropertyValueFactory<Accommodation, Integer>("floor"));
        colNrPok.setCellValueFactory(new PropertyValueFactory<Accommodation, Integer>("room_nr"));
        colWielkPok.setCellValueFactory(new PropertyValueFactory<Accommodation, Integer>("room_size"));
        colImie.setCellValueFactory(new PropertyValueFactory<Accommodation, String>("imie"));
        colNazwisko.setCellValueFactory(new PropertyValueFactory<Accommodation, String>("nazwisko"));

        showAccomm();
    }

    public void showAccomm() {
        table.setItems(db.getAccommodation());
    }


    public void showByFloor() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../views/popups/selectFloorPopup.fxml"));
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.showAndWait();
            table.setItems(db.getAccommodationByFloor(Context.getInstance().selectedFloor));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showByRoom(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../views/popups/selectRoomPopup.fxml"));
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.showAndWait();
            table.setItems(db.getAccommodationByRoom(Context.getInstance().selectedRoom));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStudentsByFloor() {

    }

    public void backToMainMenu() throws IOException {
        VBox pane = FXMLLoader.load(getClass().getResource("../views/menu_glowne.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
