package main;

import javafx.beans.property.*;

public class PaymentHistory {
    public IntegerProperty nr_wplaty = new SimpleIntegerProperty();
    public IntegerProperty id_studenta = new SimpleIntegerProperty();
    public StringProperty imie = new SimpleStringProperty();
    public StringProperty nazwisko = new SimpleStringProperty();
    public ObjectProperty data = new SimpleObjectProperty();
    public IntegerProperty kwota_wplaty = new SimpleIntegerProperty();

    public int getNr_wplaty() {
        return nr_wplaty.get();
    }

    public int getId_studenta() {
        return id_studenta.get();
    }

    public String getImie() {
        return imie.get();
    }

    public String getNazwisko() {
        return nazwisko.get();
    }

    public Object getData() {
        return data.get();
    }

    public int getKwota_wplaty() {
        return kwota_wplaty.get();
    }
}
