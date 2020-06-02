package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentHistoryController implements Initializable {

    public AnchorPane rootPane;
    public TableColumn colNrWplaty;
    public TableColumn colIdStud;
    public TableColumn colImie;
    public TableColumn colNazwisko;
    public TableColumn colData;
    public TableColumn colKwota;
    public TableView table;

    private DbHandler db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = Context.getInstance().getDbHandler();
        colNrWplaty.setCellValueFactory(new PropertyValueFactory<PaymentHistory, Integer>("nr_wplaty"));
        colIdStud.setCellValueFactory(new PropertyValueFactory<PaymentHistory, String>("id_studenta"));
        colImie.setCellValueFactory(new PropertyValueFactory<PaymentHistory, String>("imie"));
        colNazwisko.setCellValueFactory(new PropertyValueFactory<PaymentHistory, String>("nazwisko"));
        colData.setCellValueFactory(new PropertyValueFactory<PaymentHistory, Date>("data"));
        colKwota.setCellValueFactory(new PropertyValueFactory<PaymentHistory, Integer>("kwota_wplaty"));

        showHistory();
    }

    public void showHistory() {
        table.setItems(db.getPaymentHistory());
    }

    public void showHistoryNotPaid() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/popups/selectPHbyMonthPopup.fxml"));
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.showAndWait();
            //System.out.println("MSC: "+Context.getInstance().selectedStolowka);
            table.setItems(db.getNotPaidByMonth(Context.getInstance().selectedMonth));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showHistoryByMonth(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/popups/selectPHbyMonthPopup.fxml"));
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.showAndWait();
            //System.out.println("MSC: "+Context.getInstance().selectedStolowka);
            table.setItems(db.getPaymentHistoryByMonth(Context.getInstance().selectedMonth));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPayment(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/popups/addPaymentPopup.fxml"));
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.showAndWait();
            showHistory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletePayment(){
        PaymentHistory p = (PaymentHistory) table.getSelectionModel().getSelectedItem();
        int selectedId = 0;
        try {
            selectedId = p.nr_wplaty.getValue();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdź usuwanie");
            alert.setHeaderText(null);
            alert.setContentText("Czy chcesz usunąć tą płatność?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (db.deletePaymentById(selectedId) && selectedId > 0) {
                    Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                    alertInfo.setTitle("Sukces");
                    alertInfo.setHeaderText(null);
                    alertInfo.setContentText("Usuwanie przebiegło pomyślnie");
                    alertInfo.initOwner(rootPane.getScene().getWindow());
                    alertInfo.showAndWait();
                    showHistory();
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
            alertErr2.setContentText("Proszę wybrać płatność z listy");
            alertErr2.initOwner(rootPane.getScene().getWindow());
            alertErr2.showAndWait();
        }
    }

    public void modifyPayment(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/popups/modifyPaymentPopup.fxml"));
        Parent layout;
        PaymentHistory a = (PaymentHistory) table.getSelectionModel().getSelectedItem();
        try {
            Context.getInstance().setPaymentHistory(a);
            layout = loader.load();
            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.showAndWait();
            showHistory();
        } catch (IOException e) {
            Alert alertErr2 = new Alert(Alert.AlertType.ERROR);
            alertErr2.setTitle("Błąd");
            alertErr2.setHeaderText(null);
            alertErr2.setContentText("Proszę wybrać płatność z listy");
            alertErr2.initOwner(rootPane.getScene().getWindow());
            alertErr2.showAndWait();
            e.printStackTrace();
        }
    }

    public void backToMainMenu() throws IOException {
        VBox pane = FXMLLoader.load(getClass().getClassLoader().getResource("views/menu_glowne.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
