package client.scenes;
import client.utils.EventsSingleton;
import client.utils.SelectedEventSingleton;
import com.google.inject.Inject;
import commons.Event;
import commons.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class StatisticsCtrl {
    private final MainCtrl mainCtrl;
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
    public void initializeStatistics() {
        assert statsPieChart != null : "fx:id=\"statsPieChart\" was not injected: check your FXML file 'Statistics.fxml'.";
        assert statsTotalExpenses != null : "fx:id=\"statsTotalExpenses\" was not injected: check your FXML file 'Statistics.fxml'.";

        SelectedEventSingleton selectedEventInstance = SelectedEventSingleton.getInstance();
        EventsSingleton eventsInstance = EventsSingleton.getInstance();
        Event selectedEvent = eventsInstance.getEventById(selectedEventInstance.getEventId());

        if (selectedEvent == null) {
            statsTotalExpenses.setText("Server error: event not found");
        } else {
            List<Transaction> transactions = selectedEvent.getTransactions();

            double totalExpenses = 0.0;
            Map<String, Double> expensesData = new HashMap<>();

            for (Transaction transaction : transactions) {
                totalExpenses += transaction.getMoney();

                double currentTotal;
                if (expensesData.get(transaction.getExpenseType()) == null) {
                    currentTotal = 0.0;
                }
                else {
                    currentTotal = expensesData.get(transaction.getExpenseType());
                }

                expensesData.put(transaction.getExpenseType(), currentTotal + transaction.getMoney());
            }

            ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
            for (Map.Entry<String, Double> entry : expensesData.entrySet()) {
                chartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }

            statsPieChart.setData(chartData);

            statsTotalExpenses.setText("Total Expenses: " + totalExpenses + " Eur");
        }
    }

    public void setLanguageText(ResourceBundle resourceBundle) {

    }

    /**
     * Method for the go back button in the statistics page.
     */
    public void goBack() {
        mainCtrl.showEventPage(mainCtrl.getCurrentEventID());
    }
}
