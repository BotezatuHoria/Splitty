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

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {

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
                           Pair<LanguageSelectorCtrl, Parent> language) {
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
        eventCtrl.setEventID(eventID);
        eventCtrl.setTitle();
        primaryStage.setScene(event);
    }

    /**
     * Method for showing statistics page.
     */
    public void showStatisticsPage(int eventID) {
        primaryStage.setTitle("Statistics Page");
        statisticsCtrl.setEventID(eventID);
        primaryStage.setScene(statistics);
    }

    /**
     * Method for showing the add expense page.
     */
    public void showExpensePage(int eventID) {
        primaryStage.setTitle("Add Expense");
        expenseCtrl.setEventID(eventID);
        primaryStage.setScene(expense);
    }

    /**
     * Method for showing the add participant page.
     */
    public void showAddParticipant(int eventID) {
        primaryStage.setTitle("Add participant");
        additionPageCtrl.setEventID(eventID);
        primaryStage.setScene(addParticipant);
    }

    /**
     * Method for showing the invite participants page.
     */
    public void showInviteParticipantPage(int eventID) {
        primaryStage.setTitle("Send Invites");
        inviteSendingCtrl.setEventID(eventID);
        primaryStage.setScene(inviteSend);
    }

    /**
     * Method for showing the debt page.
     */
    public void showDebtPage(int eventID) {
        primaryStage.setTitle("Open debts");
        debtSettlementCtrl.setEventID(eventID);
        primaryStage.setScene(debt);
    }

    /**
     * Method for showing the language selector.
     */
    public void showLanguage() {
        primaryStage.setTitle("Select language");
        primaryStage.setScene(language);
    }
}