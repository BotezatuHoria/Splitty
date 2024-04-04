/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes;

import client.utils.LanguageSingleton;
import client.utils.SelectedEventSingleton;
import commons.Event;
import commons.Person;
import commons.Transaction;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import client.utils.ServerUtils;

import java.util.List;
import java.util.ResourceBundle;

public class MainCtrl {

    private ServerUtils server;

    private Stage primaryStage;

    private QuoteOverviewCtrl overviewCtrl;
    private Scene overview;

    private AddQuoteCtrl addCtrl;
    private Scene add;

    private Scene starter;
    private StarterPageCtrl starterPageCtrl;

    private Scene event;

    private EventPageCtrl eventCtrl;
    private Scene statistics;

    private StatisticsCtrl statisticsCtrl;

    private Scene expense;

    private AddExpenseCtrl expenseCtrl;

    private Scene addParticipant;

    private ParticipantAdditionPageCtrl additionPageCtrl;

    private Scene editParticipant;

    private ParticipantEditPageCtrl editPageCtrl;

    private Scene inviteSend;

    private InviteSendingCtrl inviteSendingCtrl;

    private Scene debt;

    private DebtSettlementCtrl debtSettlementCtrl;

    private Scene language;

    private LanguageSelectorCtrl languageSelectorCtrl;

    private Scene adminLogin;
    private AdminLoginCtrl adminLoginCtrl;

    private Scene adminPage;
    private AdminPageCtrl adminPageCtrl;

    private Scene startSettings;
    private StartSettingsCtrl startSettingsCtrl;

    private Scene editExpensePage;
    private EditExpenseCtrl editExpenseCtrl;

    public MainCtrl() {
    }


    @SuppressWarnings({"parameterNumber", "MethodLength"})
    /**
     * Initialize method for the main controller.
     * @param primaryStage - primary stage
     * @param starter - starter page
     * @param event - event page
     * @param statistics - statistics page
     * @param expense - expense page
     * @param addParticipant - add participants page
     *
     * @param debt - debt page
     * @param language - language page
     */
    public void initialize(Stage primaryStage, Pair<StarterPageCtrl, Parent> starter,
                           Pair<EventPageCtrl, Parent> event, Pair<StatisticsCtrl, Parent> statistics,
                           Pair<AddExpenseCtrl, Parent> expense,
                           Pair<StartSettingsCtrl, Parent> startSettings,
                           Pair<ParticipantAdditionPageCtrl, Parent> addParticipant,
                           Pair<ParticipantEditPageCtrl, Parent> editParticipant,
                           Pair<InviteSendingCtrl, Parent> inviteSend,
                           Pair<DebtSettlementCtrl, Parent> debt,
                           Pair<LanguageSelectorCtrl, Parent> language,
                           Pair<AdminLoginCtrl, Parent> adminLoginPage,
                           Pair<AdminPageCtrl, Parent> adminPage,
                           Pair<EditExpenseCtrl, Parent> editExpensePage) {
        this.server = new ServerUtils();

        this.primaryStage = primaryStage;

        this.startSettingsCtrl = startSettings.getKey();
        this.startSettings = new Scene(startSettings.getValue());

        this.starterPageCtrl = starter.getKey();
        this.starter = new Scene(starter.getValue());

        this.eventCtrl = event.getKey();
        this.event = new Scene(event.getValue());

        this.statisticsCtrl = statistics.getKey();
        this.statistics = new Scene(statistics.getValue());

        this.expenseCtrl = expense.getKey();
        this.expense = new Scene(expense.getValue());

        this.additionPageCtrl = addParticipant.getKey();
        this.addParticipant = new Scene(addParticipant.getValue());

        this.editPageCtrl = editParticipant.getKey();
        this.editParticipant = new Scene(editParticipant.getValue());

        this.inviteSendingCtrl = inviteSend.getKey();
        this.inviteSend = new Scene(inviteSend.getValue());

        this.debtSettlementCtrl = debt.getKey();
        this.debt = new Scene(debt.getValue());

        this.languageSelectorCtrl = language.getKey();
        this.language = new Scene(language.getValue());

        this.adminLoginCtrl = adminLoginPage.getKey();
        this.adminLogin = new Scene(adminLoginPage.getValue());

        this.adminPageCtrl = adminPage.getKey();
        this.adminPage = new Scene(adminPage.getValue());

        this.editExpenseCtrl = editExpensePage.getKey();
        this.editExpensePage = new Scene(editExpensePage.getValue());

        LanguageSingleton languageSingleton = LanguageSingleton.getInstance();
        languageSingleton.setMainCtrl(this);
        languageSingleton.setLanguageText();

        showStartSettings();
        primaryStage.show();
    }

    /**
     * Method for showing overview.
     */
    public void showOverview() {
        primaryStage.setTitle("Quotes: Overview");
        primaryStage.setScene(overview);
        overviewCtrl.refresh();
    }

    /**
     * Method for showing the add quote.
     */
    public void showAdd() {
        primaryStage.setTitle("Quotes: Adding Quote");
        primaryStage.setScene(add);
        add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
    }

    /**
     * Method for showing the starter page.
     */
    public void showStarter() {
        primaryStage.setTitle("Starter Page");
        starterPageCtrl.updateLanguage();
        primaryStage.setScene(starter);
    }

    /**
     * Method for showing the start settings page.
     */
    public void showStartSettings() {
        primaryStage.setTitle("Start Settings Page");
        primaryStage.setScene(startSettings);
    }

    /**
     * Method for showing the event page.
     */
    public void showEventPage(int eventID) {
        primaryStage.setTitle("Event Page");
        SelectedEventSingleton selectedEventInstance = SelectedEventSingleton.getInstance();
        selectedEventInstance.setEventId(eventID);
        eventCtrl.refresh();
        eventCtrl.updatePage();
        primaryStage.setScene(event);
    }

    /**
     * Method for showing statistics page.
     */
    public void showStatisticsPage() {
        statisticsCtrl.initializeStatistics();
        primaryStage.setTitle("Statistics Page");
        primaryStage.setScene(statistics);
    }

    /**
     * Method for showing the add expense page.
     */
    public void showExpensePage() {
        primaryStage.setTitle("Add Expense");
        expenseCtrl.retrievePeopleFromDb();
        primaryStage.setScene(expense);

    }

    /**
     * Method for showing the Debt settlement page.
     */
    public void showDebtSettlementPage(){

    }

    /**
     * Method for showing the add participant page.
     */
    public void showAddParticipant() {
        primaryStage.setTitle("Add participant");
        primaryStage.setScene(addParticipant);
    }

    /**
     * Method for showing the add participant page.
     */
    public void showEditParticipant() {
        primaryStage.setTitle("Edit participant" );
        editPageCtrl.updatePage();
        primaryStage.setScene(editParticipant);
    }

    /**
     * Method for showing the invite participants page.
     */
    public void showInviteParticipantPage() {
        inviteSendingCtrl.setShareCode();
        inviteSendingCtrl.setEventTitle();
        primaryStage.setTitle("Send Invites");
        primaryStage.setScene(inviteSend);
    }

    /**
     * Method for showing the debt page.
     */
    public void showDebtPage() {
        primaryStage.setTitle("Open debts");
        debtSettlementCtrl.clear();
        debtSettlementCtrl.populateOpenDebts();
        primaryStage.setScene(debt);
    }

    /**
     * Method for showing the language selector.
     */
    public void showLanguage() {
        primaryStage.setTitle("Select language");
        primaryStage.setScene(language);
    }

    /**
     * Method for showing the admin login.
     */
    public void showAdminLogin(){
        primaryStage.setTitle("Admin Login");
        primaryStage.setScene(adminLogin);
    }

    /**
     * Method for showing the admin page.
     */
    public void showAdminPage(){
        primaryStage.setTitle("Admin Page");
        primaryStage.setScene(adminPage);
        adminPageCtrl.showEvents();
        adminPageCtrl.changeSelectedEvent();
    }

    /**
     * Get current server instance.
     * @return current server instance.
     */
    public ServerUtils getServer() {
        return this.server;
    }

    /**
     * Gets the current EventID.
     * @return the current EventID.
     */
    public int getCurrentEventID() {
        SelectedEventSingleton selectedEventInstance = SelectedEventSingleton.getInstance();
        return selectedEventInstance.getEventId();
    }

    public void showAlert(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setContentText(error);
        alert.showAndWait();
    }

    /**
     * Shows the edit expense.
     */
    public void showEditExpensePage() {
        primaryStage.setTitle("Edit Expense Page");
        editExpenseCtrl.clear();
        editExpenseCtrl.updatePage();
        primaryStage.setScene(editExpensePage);
    }

    public void setLanguageText(ResourceBundle resourceBundle) {
        startSettingsCtrl.setLanguageText(resourceBundle);
    }

    /**
     * Method that builds the string representation of a transaction with all the people inside it
     * @param id of the transaction you want to display
     * @return
     */
    public String transactionString(int id) {
        Transaction t = server.getTransactionByID(id);
        String ret = t + " by " + server.getPersonByID(t.getCreator().getId()) + " and including participants: ";
        if (t.getParticipants() == null || t.getParticipants().isEmpty()) {
            return ret + "no participants;";
        }
        for(Person p: t.getParticipants()) {
            ret += server.getPersonByID(p.getId()) + ", ";
        }
        return ret.substring(0, ret.length() - 2) + ";";
    }
}