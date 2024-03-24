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

import client.utils.EventsSingleton;
import client.utils.SelectedEventSingleton;
import commons.Event;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import client.utils.ServerUtils;

import java.util.List;

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

    public MainCtrl() {
    }


    /**
     * Initialize method for the main controller.
     * @param primaryStage - primary stage
     * @param starter - starter page
     * @param event - event page
     * @param statistics - statistics page
     * @param expense - expense page
     * @param addParticipant - add participants page
     * @param debt - debt page
     * @param language - language page
     */
    public void initialize(Stage primaryStage, Pair<StarterPageCtrl, Parent> starter,
                           Pair<EventPageCtrl, Parent> event, Pair<StatisticsCtrl, Parent> statistics,
                           Pair<AddExpenseCtrl, Parent> expense,
                           Pair<ParticipantAdditionPageCtrl, Parent> addParticipant,
                           Pair<InviteSendingCtrl, Parent> inviteSend,
                           Pair<DebtSettlementCtrl, Parent> debt,
                           Pair<LanguageSelectorCtrl, Parent> language,
                           Pair<AdminLoginCtrl, Parent> adminLoginPage,
                           Pair<AdminPageCtrl, Parent> adminPage) {
        this.server = new ServerUtils();

        EventsSingleton eventsInstance = EventsSingleton.getInstance();
        List<Event> events = server.getEvents();
        eventsInstance.setEvents(events);

        this.primaryStage = primaryStage;
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

        showStarter();
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
        primaryStage.setScene(starter);
    }

    /**
     * Method for showing the event page.
     */
    public void showEventPage(int eventID) {
        primaryStage.setTitle("Event Page");
        SelectedEventSingleton selectedEventInstance = SelectedEventSingleton.getInstance();
        selectedEventInstance.setEventId(eventID);
        eventCtrl.updatePage();
        primaryStage.setScene(event);
    }

    /**
     * Method for showing statistics page.
     */
    public void showStatisticsPage() {
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
     * Method for showing the add participant page.
     */
    public void showAddParticipant() {
        primaryStage.setTitle("Add participant");
        primaryStage.setScene(addParticipant);
    }

    /**
     * Method for showing the invite participants page.
     */
    public void showInviteParticipantPage() {
        inviteSendingCtrl.setShareCode();
        primaryStage.setTitle("Send Invites");
        primaryStage.setScene(inviteSend);

    }

    /**
     * Method for showing the debt page.
     */
    public void showDebtPage() {
        primaryStage.setTitle("Open debts");
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
}