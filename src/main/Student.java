package main;

import javafx.beans.property.*;

public class Student {
    public IntegerProperty id = new SimpleIntegerProperty();
    public StringProperty nazwisko = new SimpleStringProperty();
    public StringProperty imie = new SimpleStringProperty();
    public ObjectProperty dataUrodzenia = new SimpleObjectProperty();
    public IntegerProperty nrTelefonu = new SimpleIntegerProperty();
    public StringProperty email = new SimpleStringProperty();
    public StringProperty adres = new SimpleStringProperty();
    public StringProperty kierunek = new SimpleStringProperty();
    public IntegerProperty rokStudiow = new SimpleIntegerProperty();
    public IntegerProperty czynsz = new SimpleIntegerProperty();
    public StringProperty rodzajStolowki = new SimpleStringProperty();

    public int getId() {
        return id.get();
    }

    public String getNazwisko() {
        return nazwisko.get();
    }

    public String getImie() {
        return imie.get();
    }

    public Object getDataUrodzenia() {
        return dataUrodzenia.get();
    }

    public int getNrTelefonu() {
        return nrTelefonu.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getAdres() {
        return adres.get();
    }

    public String getKierunek() {
        return kierunek.get();
    }

    public int getRokStudiow() {
        return rokStudiow.get();
    }

    public int getCzynsz() {
        return czynsz.get();
    }

    public String getRodzajStolowki() {
        return rodzajStolowki.get();
    }
}
