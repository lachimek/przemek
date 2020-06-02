package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
        loader.setLocation(getClass().getClassLoader().getResource("views/popups/selectFloorPopup.fxml"));
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
        loader.setLocation(getClass().getClassLoader().getResource("views/popups/selectRoomPopup.fxml"));
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

    public void showFreeRooms() {
        List<Room> rooms;
        rooms = db.getNotFullRooms();
        StringBuilder content = new StringBuilder();
        for(Room r : rooms){
            content.append(r.toString()).append("\n");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Wolnej pokoje");
        alert.setHeaderText(null);
        alert.setContentText(content.toString());
        alert.showAndWait();
    }

    public void addNewAccomm() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/popups/addAccommPopup.fxml"));
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.showAndWait();
            showAccomm();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modifyAccomm() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/popups/modifyAccommPopup.fxml"));
        Parent layout;
        Accommodation a = (Accommodation) table.getSelectionModel().getSelectedItem();
        try {
            Context.getInstance().setAccommodation(a);
            layout = loader.load();
            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.showAndWait();
            showAccomm();
        } catch (IOException e) {
            Alert alertErr2 = new Alert(Alert.AlertType.ERROR);
            alertErr2.setTitle("Błąd");
            alertErr2.setHeaderText(null);
            alertErr2.setContentText("Proszę wybrać studenta z listy");
            alertErr2.initOwner(rootPane.getScene().getWindow());
            alertErr2.showAndWait();
        }
    }

    public void deleteAccomm(){
        Accommodation a = (Accommodation) table.getSelectionModel().getSelectedItem();
        int selectedId = 0;
            try {
            selectedId = a.id_accomm.getValue();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdź usuwanie");
            alert.setHeaderText(null);
            alert.setContentText("Czy chcesz usunąć to zakwaterowanie?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (db.deleteAccommById(selectedId) && selectedId > 0) {
                    Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                    alertInfo.setTitle("Sukces");
                    alertInfo.setHeaderText(null);
                    alertInfo.setContentText("Usuwanie przebiegło pomyślnie");
                    alertInfo.initOwner(rootPane.getScene().getWindow());
                    alertInfo.showAndWait();
                    showAccomm();
                } else {
                    Alert alertErr1 = new Alert(Alert.AlertType.ERROR);
                    alertErr1.setTitle("Błąd");
                    alertErr1.setHeaderText(null);
                    alertErr1.setContentText("Wystąpił nieoczekiwany błąd");
                    alertErr1.initOwner(rootPane.getScene().getWindow());
                    alertErr1.showAndWait();
                }
            }
        }catch (NullPointerException e){
            Alert alertErr2 = new Alert(Alert.AlertType.ERROR);
            alertErr2.setTitle("Błąd");
            alertErr2.setHeaderText(null);
            alertErr2.setContentText("Proszę wybrać zakwaterowanie z listy");
            alertErr2.initOwner(rootPane.getScene().getWindow());
            alertErr2.showAndWait();
        }
    }

    public void backToMainMenu() throws IOException {
        VBox pane = FXMLLoader.load(getClass().getClassLoader().getResource("views/menu_glowne.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
