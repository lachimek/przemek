package controllers.popupControllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import main.Context;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SelectPHMonthController implements Initializable {
    public Button btn;
    public ComboBox<String> comboMonth;
    private final List<String> months = Arrays.asList("Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec",
            "Lipiec", "Sierpień", "Wrzesień", "Pażdziernik", "Listopad", "Grudzień");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboMonth.getItems().addAll(months);
    }

    public void selectYear() {
        int monthId = months.indexOf(comboMonth.getValue())+1;
        //System.out.println(monthId);
        Context.getInstance().selectedMonth = monthId;
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
}
