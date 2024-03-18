package client.scenes;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class StatisticsCtrl {
    private final MainCtrl mainCtrl;
    private int eventID = -1;
    @FXML // fx:id="statsPieChart"
    private PieChart statsPieChart;

    @FXML // fx:id="statsTotalExpenses"
    private Label statsTotalExpenses;
    @FXML // fx:id="goBackButton"
    private Button goBackButton; // Value injected by FXMLLoader

    /**
     * Constructor for the statistics controller.
     * @param mainCtrl - reference to the main controller.
     */
    @Inject
    public StatisticsCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert statsPieChart != null : "fx:id=\"statsPieChart\" was not injected: check your FXML file 'Statistics.fxml'.";
        assert statsTotalExpenses != null : "fx:id=\"statsTotalExpenses\" was not injected: check your FXML file 'Statistics.fxml'.";
    }

    /**
     * Method for the go back button in the statistics page.
     */
    public void goBack() {
        mainCtrl.showEventPage(eventID);
    }

    /**
     * Setter for eventID.
     * @param eventID of the current eent
     */
    public void setEventID(int eventID) {
        this.eventID = eventID;
    }
}
