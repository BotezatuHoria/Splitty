/**
 * Sample Skeleton for 'EventPage.fxml' Controller Class
 */

package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

import commons.Person;

import commons.Transaction;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.bytebuddy.dynamic.scaffold.MethodGraph;


import java.util.ArrayList;
import java.util.List;

public class EventPageCtrl {
    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    @FXML // fx:id="addExpense"
    private Button addExpense; // Value injected by FXMLLoader

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
        List<Person> people = server.getPeopleInCurrentEvent(mainCtrl.getCurrentEventID());
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
        List<Transaction> transactions = server.getTransactions(mainCtrl.getCurrentEventID());
        for(Transaction transaction: transactions) {
            listTransactions.getItems().add(transaction);
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



}


