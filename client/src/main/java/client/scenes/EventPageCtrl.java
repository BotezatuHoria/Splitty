/**
 * Sample Skeleton for 'EventPage.fxml' Controller Class
 */

package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

import commons.Person;

import commons.Transaction;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EventPageCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    private ObservableList<Person> data;

    private ObservableList<Transaction> dataTransactions;

    @FXML // fx:id="namePane"
    private AnchorPane namePane;

    @FXML // fx:id="nameField"
    private TextField nameField;

    @FXML // fx:id="saveName"
    private Button saveName;

    @FXML // fx:id="addExpense"
    private Button addExpense; // Value injected by FXMLLoader

    @FXML // fx:id="SendInvites"
    private Button SendInvites; // Value injected by FXMLLoader

    @FXML // fx:id="participantsLabel"
    private Label participantsLabel; // Value injected by FXMLLoader

    @FXML // fx:id="expensesLabel"
    private Label expensesLabel; // Value injected by FXMLLoader

    @FXML // fx:id="EditParticipant"
    private Button EditParticipant; // Value injected by FXMLLoader

    @FXML // fx:id="AddParticipant"
    private Button AddParticipant; // Value injected by FXMLLoader

    @FXML // fx:id="AddExpense"
    private Button AddExpense; // Value injected by FXMLLoader

    @FXML // fx:id="SettleDebts"
    private Button SettleDebts; // Value injected by FXMLLoader

    @FXML // fx:id="addParticipant"
    private Button addParticipant; // Value injected by FXMLLoader

    @FXML // fx:id="allExpenses"
    private Button allExpenses; // Value injected by FXMLLoader

    @FXML // fx:id="editParticipants"
    private Button editParticipants; // Value injected by FXMLLoader

    @FXML // fx:id="fromParticipant"
    private Button fromParticipant; // Value injected by FXMLLoader

    @FXML // fx:id="includingParticipant"
    private Button includingParticipant; // Value injected by FXMLLoader

    @FXML // fx:id="sendInvites"
    private Button sendInvites; // Value injected by FXMLLoader

    @FXML // fx:id="settleDebts"
    private Button settleDebts; // Value injected by FXMLLoader

    @FXML // fx:id="homeButton"
    private Button homeButton; // Value injected by FXMLLoader

    @FXML // fx:id="showStatistics"
    private Button showStatistics; // Value injected by FXMLLoader

    @FXML // fx:id="eventTitle"
    private Label eventTitle;

    @FXML // fx:id="participantsList"
    private Label participantsList;

    @FXML // fx:id="participantsScroll"
    private ComboBox<Person> participantsScroll;

    @FXML // fx:id="listTransactions"
    private ListView<Transaction> listTransactions;

    @FXML // fx:id="editExpense"
    private Button editExpense;

    @FXML // fx:id="editName"
    private Button editName;


    /**
     * Constructor for EventPageCtrl.
     *
     * @param mainCtrl - reference to the main controller
     * @param server
     */
    @Inject
    public EventPageCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }



    /**
     * Method for accessing the expense page.
     */
    public void showAddExpensePage() {
        mainCtrl.showExpensePage();
    }

    /**
     * Method for accessing the add participants page.
     */
    public void addParticipants() {
        mainCtrl.showAddParticipant();
    }

    /**
     * Method for accessing the edit participants page.
     */
    public void editParticipants() {
        mainCtrl.showEditParticipant();
    }

    /**
     * Method for accessing the sending invites page.
     */
    public void sendInvites() {
        mainCtrl.showInviteParticipantPage();
    }

    /**
     * Method for accessing the debts page.
     */
    public void settleDebts() {
        mainCtrl.showDebtPage();
    }

    /**
     * Method for going back to the main pge.
     */
    public void goHome() {
        mainCtrl.showStarter();
    }

    /**
     * Method for changing to the statistics page.
     */
    public void showStatistics() {mainCtrl.showStatisticsPage();}

    /**
     * Sets the title to the current event.
     */
    public void setTitle() {
        eventTitle.setText(server.getEventByID(mainCtrl.getCurrentEventID()).getTitle());
    }


    public void editName() {
        namePane.setVisible(true);
    }

    public void saveName() {
        String name = nameField.getText();
        eventTitle.setText(name);
        namePane.setVisible(false);
    }

    /**
     * Method that updates the title, people and transactions on that page.
     */
    public void updatePage() {
        clear();
        setTitle();
        displayParticipants();
        displayTransactions();
    }

    /**
     * Displays participants on that page for the current event.
     */
    private void displayParticipants() {
        String display = "";
        List<Person> people = data;
        participantsScroll.getItems().clear();
        for (Person person: people) {
            display += person + ", ";
            participantsScroll.getItems().add(person);
        }
        if (!display.isBlank()) {display = display.substring(0, display.length() - 2);}
        participantsList.setText(display);
        System.out.println("This is selected" + participantsScroll.getSelectionModel().getSelectedItem());
    }

    /**
     * Selects the person for which transaction should be displayed.
     */
    public void selectParticipant() {
        Person person = participantsScroll.getSelectionModel().getSelectedItem();
        System.out.println(person);
        if (person != null) {
            fromParticipant.setText("From " + person);
            includingParticipant.setText("Including " + person);
        }
    }

    /**
     * Displays the transactions in the current event.
     */
    public void displayTransactions() {
        listTransactions.getItems().clear();
        for (Transaction t : dataTransactions) {
            listTransactions.getItems().add(t);
        }
    }

    /**
     * Method that displays all the transactions from a certain person.
     */
    public void displayFrom() {
        listTransactions.getItems().clear();
        if (participantsScroll.getValue() == null) {return;}
        int creator = participantsScroll.getValue().getId();

        List<Transaction> transactions = server.getTransactions(mainCtrl.getCurrentEventID());

        for (Transaction transaction: transactions) {
            if (transaction.getCreator().getId() == creator) {
                listTransactions.getItems().add(transaction);
            }
        }
    }

    /**
     * Method that displays all the transactions including a certain person.
     */
    public void displayIncluding() {
        listTransactions.getItems().clear();
        if (participantsScroll.getValue() == null) {return;}
        int included = participantsScroll.getValue().getId();

        List<Transaction> transactions = server.getTransactions(mainCtrl.getCurrentEventID());

        for (Transaction transaction: transactions) {
            if (transaction.getParticipantsIds().contains(included)) {
                listTransactions.getItems().add(transaction);
            }
        }

    }

    /**
     * Method that clears the page of all previous inputs.
     */
    public void clear() {
        participantsScroll.getItems().clear();
        listTransactions.getItems().clear();
        fromParticipant.setText("From participant...");
        includingParticipant.setText("Including participant...");
    }


    public void refresh() {
        var people = server.getPeopleInCurrentEvent(mainCtrl.getCurrentEventID());
        var transactions = server.getTransactions(mainCtrl.getCurrentEventID());
        data = FXCollections.observableList(people);
        dataTransactions = FXCollections.observableList(transactions);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        namePane.setVisible(false);
        server.registerForMessages("/topic/events/people", Person.class, person -> {
            Platform.runLater(() -> {
                data.add(person);
                refresh();
                updatePage();
            });
        });
        //server.registerForMessages("/topic/events/transactions", Transaction.class, transaction -> {
        //    Platform.runLater(() -> {
        //        dataTransactions.add(transaction);
        //        refresh();
        //        updatePage();
        //    });
        //});
        server.registerForUpdates(t -> {
            Platform.runLater(() -> {
                dataTransactions.add(t);
                refresh();
                updatePage();
            });
        });
    }

    /**
     * Method that opens the Edit event page.
     */
    public void showEditExpensePage() {
        mainCtrl.showEditExpensePage();
    }

    public void setLanguageText(ResourceBundle resourceBundle) {
        SendInvites.setText(resourceBundle.getString("sendInvites.button"));
        participantsLabel.setText(resourceBundle.getString("participants"));
        EditParticipant.setText(resourceBundle.getString("edit.button"));
        AddParticipant.setText(resourceBundle.getString("addParticipant.button"));
        expensesLabel.setText(resourceBundle.getString("expenses"));
        allExpenses.setText(resourceBundle.getString("all.button"));
        fromParticipant.setText(resourceBundle.getString("from.button"));
        includingParticipant.setText(resourceBundle.getString("including.button"));
        AddExpense.setText(resourceBundle.getString("addExpense.button"));
        SettleDebts.setText(resourceBundle.getString("settleDebts.button"));
        showStatistics.setText(resourceBundle.getString("showStats.button"));
    }

    public void stop() {
        server.stop();
    }
}


