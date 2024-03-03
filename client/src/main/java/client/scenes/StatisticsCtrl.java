package client.scenes;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class StatisticsCtrl {
    private final MainCtrl mainCtrl;
    @FXML // fx:id="statsPieChart"
    private PieChart statsPieChart;

    @FXML // fx:id="statsTotalExpenses"
    private Label statsTotalExpenses;

    @Inject
    public StatisticsCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert statsPieChart != null : "fx:id=\"statsPieChart\" was not injected: check your FXML file 'Statistics.fxml'.";
        assert statsTotalExpenses != null : "fx:id=\"statsTotalExpenses\" was not injected: check your FXML file 'Statistics.fxml'.";
    }
}
