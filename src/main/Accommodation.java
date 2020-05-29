package main;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Accommodation {
    public IntegerProperty id_accomm = new SimpleIntegerProperty();
    public IntegerProperty floor = new SimpleIntegerProperty();
    public IntegerProperty room_nr = new SimpleIntegerProperty();
    public IntegerProperty room_size = new SimpleIntegerProperty();
    public StringProperty imie = new SimpleStringProperty();
    public StringProperty nazwisko = new SimpleStringProperty();

    public int getId_accomm() {
        return id_accomm.get();
    }

    public int getFloor() {
        return floor.get();
    }

    public int getRoom_nr() {
        return room_nr.get();
    }

    public int getRoom_size() {
        return room_size.get();
    }

    public String getImie() {
        return imie.get();
    }

    public String getNazwisko() {
        return nazwisko.get();
    }
}
