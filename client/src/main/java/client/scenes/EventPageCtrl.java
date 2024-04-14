/**
 * Sample Skeleton for 'EventPage.fxml' Controller Class
 */

package client.scenes;

import client.utils.LanguageSingleton;
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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.lang3.tuple.Pair;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EventPageCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    private ObservableList<Person> data;

    private ObservableList<Transaction> dataTransactions;

    @FXML
    private Button addParticipant;

    @FXML
    private Button addExpense;

    @FXML
    private Button allExpenses;

    @FXML
    private Button editExpense;

    @FXML
    private Button editName;

    @FXML
    private Button editParticipants;

    @FXML
    private Label eventTitle;

    @FXML
    private Label expensesLabel;

    @FXML
    private Button fromParticipant;

    @FXML
    private Button giveMoney;

    @FXML
    private Button homeButton;

    @FXML
    private Button includingParticipant;

    @FXML
    private ComboBox languageSelector;

    @FXML
    private ListView<String> listTransactions;

    @FXML
    private TextField nameField;

    @FXML
    private AnchorPane namePane;

    @FXML
    private AnchorPane pane;

    @FXML
    private Label participantsLabel;

    @FXML
    private Label participantsList;

    @FXML
    private ComboBox<Person> participantsScroll;

    @FXML
    private Button saveName;

    @FXML
    private Button sendInvites;

    @FXML
    private Button settingButton;

    @FXML
    private Button settleDebts;

    @FXML
    private Button showStatistics;


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
     * Method for accessing the debt overview page.
     */
    public void settleDebts() {
        mainCtrl.showDebtOverviewPage();
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
        nameField.setText(eventTitle.getText());
    }

    public void saveName() {
        String name = nameField.getText().trim();
        if (!name.isEmpty()) {
            eventTitle.setText(name);
            namePane.setVisible(false);
            server.updateTitleEvent(mainCtrl.getCurrentEventID(), name);
        }
        else {
            mainCtrl.showAlert("Please enter a non-empty event name!");
        }

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
        if(participantsScroll.getSelectionModel().getSelectedItem()!= null){
            System.out.println("This participant added to the event: " + participantsScroll.getSelectionModel().getSelectedItem());
        }

    }

    /**
     * Selects the person for which transaction should be displayed.
     */
    public void selectParticipant() {
        Person person = participantsScroll.getSelectionModel().getSelectedItem();
        if(person != null){
            System.out.println("This participant is selected: " + person);
        }

        if (person != null) {
            ResourceBundle resourceBundle = LanguageSingleton.getInstance().getResourceBundle();

            fromParticipant.setText(resourceBundle.getString("label.from") + person);
            includingParticipant.setText(resourceBundle.getString("label.including") + person);
        }
    }

    /**
     * Displays the transactions in the current event.
     */
    public void displayTransactions() {
        listTransactions.getItems().clear();
        for (Transaction t : dataTransactions) {
            listTransactions.getItems().add(mainCtrl.transactionString(t.getId()));
        }
    }

    public void giveMoneyPage() {
        mainCtrl.showGiveMoneyPage();
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
                listTransactions.getItems().add(mainCtrl.transactionString(transaction.getId()));
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
                listTransactions.getItems().add(mainCtrl.transactionString(transaction.getId()));
            }
        }

    }

    /**
     * Method that clears the page of all previous inputs.
     */
    public void clear() {
        ResourceBundle resourceBundle = LanguageSingleton.getInstance().getResourceBundle();

        participantsScroll.getItems().clear();
        listTransactions.getItems().clear();
        fromParticipant.setText(resourceBundle.getString("label.fromParticipant"));
        includingParticipant.setText(resourceBundle.getString("label.includingParticipant"));
    }


    public void refresh() {
        var people = server.getPeopleInCurrentEvent(mainCtrl.getCurrentEventID());
        var transactions = server.getTransactions(mainCtrl.getCurrentEventID());
        data = FXCollections.observableList(people);
        dataTransactions = FXCollections.observableList(transactions);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainCtrl.handleEnterKeyPress(homeButton, this::goHome);
        mainCtrl.handleEnterKeyPress(settingButton, this::showSettingsPage);
        mainCtrl.handleEnterKeyPress(sendInvites, this::sendInvites);
        mainCtrl.handleEnterKeyPress(editName, this::editName);
        mainCtrl.handleEnterKeyPress(editParticipants, this::editParticipants);
        mainCtrl.handleEnterKeyPress(addParticipant, this::addParticipants);
        mainCtrl.handleEnterKeyPress(allExpenses, this::displayTransactions);
        mainCtrl.handleEnterKeyPress(fromParticipant, this::displayFrom);
        mainCtrl.handleEnterKeyPress(includingParticipant, this::displayIncluding);
        mainCtrl.handleEnterKeyPress(addExpense, this::showAddExpensePage);
        mainCtrl.handleEnterKeyPress(editExpense, this::showEditExpensePage);
        mainCtrl.handleEnterKeyPress(giveMoney, this::giveMoneyPage);
        mainCtrl.handleEnterKeyPress(settleDebts, this::settleDebts);
        mainCtrl.handleEnterKeyPress(showStatistics,this::showStatistics);
        mainCtrl.handleEnterKeyPress(saveName, this::saveName);
        namePane.setVisible(false);
        server.registerForMessages("/topic/events/people", Person.class, person -> {
            Platform.runLater(() -> {
                if (data.contains(person)) {
                    data.remove(person);
                    data.add(person);
                }
                else {
                    data.add(person);
                }
                updatePage();
            });
        });
        server.registerForUpdates(t -> {
            Platform.runLater(() -> {
                dataTransactions.add(t);
                updatePage();
            });
        });
        new Thread(() -> {server.registerForMessages("/topic/event", Object.class, object -> {
            if (mainCtrl.getCurrentEventID() != 0) {
                Platform.runLater(() -> {
                    this.refresh();
                    this.updatePage();
                });
            }
        });}).start();
    }
    /**
     * Method that opens the Edit event page.
     */
    public void showEditExpensePage() {
        mainCtrl.showEditExpensePage();
    }

    public void setLanguageText(ResourceBundle resourceBundle) {
        fromParticipant.setText(resourceBundle.getString("from.button"));
        includingParticipant.setText(resourceBundle.getString("including.button"));
        addExpense.setText(resourceBundle.getString("addExpense.button"));
        settleDebts.setText(resourceBundle.getString("settleDebts.button"));
        showStatistics.setText(resourceBundle.getString("showStats.button"));
        sendInvites.setText(resourceBundle.getString("sendInvites.button"));
        participantsLabel.setText(resourceBundle.getString("participants"));
        expensesLabel.setText(resourceBundle.getString("expenses"));
        allExpenses.setText(resourceBundle.getString("all.button"));
        participantsScroll.setPromptText(resourceBundle.getString("edit.participant.comboBoxPrompt"));
        editExpense.setText(resourceBundle.getString("edit.expense"));
        giveMoney.setText(resourceBundle.getString("giveMoney.button"));
    }

    public void stop() {
        server.stop();
    }

    public void showSettingsPage(){
        mainCtrl.showStartSettings();
    }

    void initializeLanguages() {
        server.initializeLanguages(languageSelector);
    }

    public void setLanguageSelector() {
        server.setLanguageSelector(languageSelector);
    }

    public void updateLanguage() {
        // Show current language
        Pair<String, Image> currentLanguage = LanguageSingleton.getInstance().getLanguage();
    }
}


