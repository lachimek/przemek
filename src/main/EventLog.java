package main;

import javafx.beans.property.*;

public class EventLog {
    public IntegerProperty id = new SimpleIntegerProperty();
    public StringProperty user = new SimpleStringProperty();
    public ObjectProperty data = new SimpleObjectProperty();
    public StringProperty action = new SimpleStringProperty();
    public IntegerProperty target = new SimpleIntegerProperty();

    public int getId() {
        return id.get();
    }

    public String getUser() {
        return user.get();
    }

    public Object getData() {
        return data.get();
    }

    public String getAction() {
        return action.get();
    }

    public int getTarget() {
        return target.get();
    }
}
