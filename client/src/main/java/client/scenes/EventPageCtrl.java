/**
 * Sample Skeleton for 'EventPage.fxml' Controller Class
 */

package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

import commons.Person;

import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.util.List;
import java.util.Set;

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
     * Setts the title to the current event.
     */
    public void setTitle() {
        eventTitle.setText(server.getEventByID(mainCtrl.getCurrentEventID()).getTitle());
    }


    /**
     * Method that updates the title, people and transactions on that page.
     */
    public void updatePage() {
        setTitle();
        displayParticipants();
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
    }

    /**
     * Selects the person for which transaction should be displayed.
     */
    public void selectParticipant() {
        Person person = participantsScroll.getSelectionModel().getSelectedItem();
        if (person != null) {
            fromParticipant.setText("From " + person);
            includingParticipant.setText("Including " + person);
        }
    }

}


