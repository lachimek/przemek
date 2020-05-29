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
import main.PaymentHistory;
import main.Student;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
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
        loader.setLocation(getClass().getResource("../views/popups/selectPHbyMonthPopup.fxml"));
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
        loader.setLocation(getClass().getResource("../views/popups/selectPHbyMonthPopup.fxml"));
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

    public void backToMainMenu() throws IOException {
        VBox pane = FXMLLoader.load(getClass().getResource("../views/menu_glowne.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
