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
import commons.Person;
import commons.Transaction;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import client.utils.ServerUtils;
import java.util.ResourceBundle;

public class MainCtrl {

    private ServerUtils server;

    private Stage primaryStage;

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

    private Scene adminLogin;
    private AdminLoginCtrl adminLoginCtrl;

    private Scene adminPage;
    private AdminPageCtrl adminPageCtrl;

    private Scene startSettings;
    private StartSettingsCtrl startSettingsCtrl;

    private Scene editExpensePage;
    private EditExpenseCtrl editExpenseCtrl;

    private Scene debtOverview;
    private DebtOverviewPageCtrl debtOverviewPageCtrl;

    private String styleSheet;
    private String contrastStyleSheet;

    private Scene giveMoneyPage;
    private GiveMoneyCtrl giveMoneyCtrl;

    public MainCtrl() {
    }


    @SuppressWarnings({"parameterNumber", "MethodLength", "CyclomaticComplexity"})
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
                           Pair<AdminLoginCtrl, Parent> adminLoginPage,
                           Pair<AdminPageCtrl, Parent> adminPage,
                           Pair<EditExpenseCtrl, Parent> editExpensePage,
                           Pair<DebtOverviewPageCtrl, Parent> debtOverview,
                           Pair<GiveMoneyCtrl, Parent> giveMoney){
        this.server = new ServerUtils();

        this.primaryStage = primaryStage;

        styleSheet = "/StyleNormal.css";
        contrastStyleSheet = "/StyleContrast.css";

        this.startSettingsCtrl = startSettings.getKey();
        this.startSettings = new Scene(startSettings.getValue());
        this.startSettings.getStylesheets().add(styleSheet);

        this.starterPageCtrl = starter.getKey();
        this.starter = new Scene(starter.getValue());
        this.starter.getStylesheets().add(styleSheet);

        this.eventCtrl = event.getKey();
        this.event = new Scene(event.getValue());
        this.event.getStylesheets().add(styleSheet);

        this.statisticsCtrl = statistics.getKey();
        this.statistics = new Scene(statistics.getValue());
        this.statistics.getStylesheets().add(styleSheet);

        this.expenseCtrl = expense.getKey();
        this.expense = new Scene(expense.getValue());
        this.expense.getStylesheets().add(styleSheet);

        this.additionPageCtrl = addParticipant.getKey();
        this.addParticipant = new Scene(addParticipant.getValue());
        this.addParticipant.getStylesheets().add(styleSheet);

        this.editPageCtrl = editParticipant.getKey();
        this.editParticipant = new Scene(editParticipant.getValue());
        this.editParticipant.getStylesheets().add(styleSheet);

        this.inviteSendingCtrl = inviteSend.getKey();
        this.inviteSend = new Scene(inviteSend.getValue());
        this.inviteSend.getStylesheets().add(styleSheet);

        this.debtSettlementCtrl = debt.getKey();
        this.debt = new Scene(debt.getValue());
        this.debt.getStylesheets().add(styleSheet);

        this.debtOverview = new Scene(debtOverview.getValue());
        this.debtOverviewPageCtrl = debtOverview.getKey();
        this.debtOverview.getStylesheets().add(styleSheet);

        this.adminLoginCtrl = adminLoginPage.getKey();
        this.adminLogin = new Scene(adminLoginPage.getValue());
        this.adminLogin.getStylesheets().add(styleSheet);

        this.adminPageCtrl = adminPage.getKey();
        this.adminPage = new Scene(adminPage.getValue());
        this.adminPage.getStylesheets().add(styleSheet);

        this.editExpenseCtrl = editExpensePage.getKey();
        this.editExpensePage = new Scene(editExpensePage.getValue());
        this.editExpensePage.getStylesheets().add(styleSheet);

        this.giveMoneyCtrl = giveMoney.getKey();
        this.giveMoneyPage = new Scene(giveMoney.getValue());
        this.giveMoneyPage.getStylesheets().add(styleSheet);



        startSettingsCtrl.initializeLanguages();
        starterPageCtrl.initializeLanguages();
        eventCtrl.initializeLanguages();

        LanguageSingleton languageSingleton = LanguageSingleton.getInstance();
        languageSingleton.setLanguageByCode(ServerUtils.getConfig().getClientsLanguage());
        languageSingleton.setLanguageText();

        showStartSettings();
        primaryStage.show();

        //keyboard combinations
        KeyCombination ctrle = new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN);
        KeyCombination ctrli = new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN);
        KeyCombination ctrls = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
        KeyCombination ctrlp = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);
        KeyCombination ctrld = new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN);
        KeyCombination ctrlh = new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN);
        KeyCombination ctrlb = new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN);

        //shortcuts
        this.event.setOnKeyPressed(event1 -> {
            if (ctrle.match(event1)){
                showExpensePage();
            } else if (ctrli.match(event1)) {
                showInviteParticipantPage();
            } else if (ctrls.match(event1)){
                showStatisticsPage();
            } else if (ctrlp.match(event1)){
                showAddParticipant();
            } else if (ctrld.match(event1)){
                showDebtOverviewPage();
            } else if (ctrlh.match(event1)) {
                showStarter();
            }
        });

        this.expense.setOnKeyPressed(event1 -> {
            if(ctrlb.match(event1)){
                showEventPage(getCurrentEventID());
            }
        });

        this.inviteSend.setOnKeyPressed(event1 -> {
            if(ctrlb.match(event1)){
                showEventPage(getCurrentEventID());
            }
        });

        this.statistics.setOnKeyPressed(event1 -> {
            if(ctrlb.match(event1)){
                showEventPage(getCurrentEventID());
            }
        });

        this.addParticipant.setOnKeyPressed(event1 -> {
            if(ctrlb.match(event1)){
                showEventPage(getCurrentEventID());
            }
        });

        this.debtOverview.setOnKeyPressed(event1 -> {
            if(ctrlb.match(event1)){
                showEventPage(getCurrentEventID());
            }
        });
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
        eventCtrl.updateLanguage();
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
     * Method for showing the debt overview page.
     */
    public void showDebtOverviewPage() {
        primaryStage.setTitle("Debt Overview");
        debtOverviewPageCtrl.populateDebtList();
        primaryStage.setScene(debtOverview);
    }
    /**
     * Method for showing the debt page.
     */
    public void showDebtPage() {
        primaryStage.setTitle("Open debts");
        debtSettlementCtrl.clear();
        debtSettlementCtrl.allDebts();
        primaryStage.setScene(debt);
    }

    /**
<<<<<<< HEAD
=======
     * Method for showing the debt page for a specific person.
     * @param person - the person to show the debt page for.
     */
    public void showDebtPageForSpecificPerson(Person person) {
        debtSettlementCtrl.clear();
        debtSettlementCtrl.debtsOfPerson(person.getId());
        primaryStage.setTitle("Debts for " + person.getFirstName() + " " + person.getLastName());
        primaryStage.setScene(debt);
    }

    /**
>>>>>>> main
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
        starterPageCtrl.setLanguageSelector();
        startSettingsCtrl.setLanguageSelector();
        eventCtrl.setLanguageSelector();

        startSettingsCtrl.setLanguageText(resourceBundle);
        starterPageCtrl.setLanguageText(resourceBundle);
        eventCtrl.setLanguageText(resourceBundle);
        statisticsCtrl.setLanguageText(resourceBundle);
        expenseCtrl.setLanguageText(resourceBundle);
        additionPageCtrl.setLanguageText(resourceBundle);
        editPageCtrl.setLanguageText(resourceBundle);
        inviteSendingCtrl.setLanguageText(resourceBundle);
        debtSettlementCtrl.setLanguageText(resourceBundle);
        adminLoginCtrl.setLanguageText(resourceBundle);
        adminPageCtrl.setLanguageText(resourceBundle);
        editExpenseCtrl.setLanguageText(resourceBundle);
        giveMoneyCtrl.setLanguageText(resourceBundle);
    }

    /**
     * Method that builds the string representation of a transaction with all the people inside it.
     * @param id of the transaction you want to display.
     * @return
     */
    public String transactionString(int id) {
        Transaction t = server.getTransactionByID(id);
        String byLabel = LanguageSingleton.getInstance().getResourceBundle().getString("by.label");
        String includingParticipantsLabel = LanguageSingleton.getInstance().getResourceBundle().getString("including.participants");
        String noParticipants = LanguageSingleton.getInstance().getResourceBundle().getString("no.participants");

        String ret = t + " " + byLabel +  " " + server.getPersonByID(t.getCreator().getId()) + " " + includingParticipantsLabel + " ";
        if (t.getParticipants() == null || t.getParticipants().isEmpty()) {
            return ret + noParticipants;
        }
        for(Person p: t.getParticipants()) {
            ret += server.getPersonByID(p.getId()) + ", ";
        }
        return ret.substring(0, ret.length() - 2) + ";";
    }

    /**
     * Changes all the pages from the normal contrast to the high contrast stylesheet.
     */
    public void highContrast(){
        this.startSettings.getStylesheets().remove(styleSheet);
        this.startSettings.getStylesheets().add(contrastStyleSheet);

        this.starter.getStylesheets().remove(styleSheet);
        this.starter.getStylesheets().add(contrastStyleSheet);

        this.event.getStylesheets().remove(styleSheet);
        this.event.getStylesheets().add(contrastStyleSheet);

        this.statistics.getStylesheets().remove(styleSheet);
        this.statistics.getStylesheets().add(contrastStyleSheet);

        this.expense.getStylesheets().remove(styleSheet);
        this.expense.getStylesheets().add(contrastStyleSheet);

        this.addParticipant.getStylesheets().remove(styleSheet);
        this.addParticipant.getStylesheets().add(contrastStyleSheet);

        this.editParticipant.getStylesheets().remove(styleSheet);
        this.editParticipant.getStylesheets().add(contrastStyleSheet);

        this.inviteSend.getStylesheets().remove(styleSheet);
        this.inviteSend.getStylesheets().add(contrastStyleSheet);

        this.debt.getStylesheets().remove(styleSheet);
        this.debt.getStylesheets().add(contrastStyleSheet);

        this.debtOverview.getStylesheets().remove(styleSheet);
        this.debtOverview.getStylesheets().add(contrastStyleSheet);

        this.adminLogin.getStylesheets().remove(styleSheet);
        this.adminLogin.getStylesheets().add(contrastStyleSheet);

        this.adminPage.getStylesheets().remove(styleSheet);
        this.adminPage.getStylesheets().add(contrastStyleSheet);

        this.editExpensePage.getStylesheets().remove(styleSheet);
        this.editExpensePage.getStylesheets().add(contrastStyleSheet);

        this.giveMoneyPage.getStylesheets().remove(styleSheet);
        this.editExpensePage.getStylesheets().add(contrastStyleSheet);
    }

    /**
     * Changes all the pages from the high contrast to normal contrast stylesheet.
     */
    public void normalContrast(){
        this.startSettings.getStylesheets().remove(contrastStyleSheet);
        this.startSettings.getStylesheets().add(styleSheet);

        this.starter.getStylesheets().remove(contrastStyleSheet);
        this.starter.getStylesheets().add(styleSheet);

        this.event.getStylesheets().remove(contrastStyleSheet);
        this.event.getStylesheets().add(styleSheet);

        this.statistics.getStylesheets().remove(contrastStyleSheet);
        this.statistics.getStylesheets().add(styleSheet);

        this.expense.getStylesheets().remove(contrastStyleSheet);
        this.expense.getStylesheets().add(styleSheet);

        this.addParticipant.getStylesheets().remove(contrastStyleSheet);
        this.addParticipant.getStylesheets().add(styleSheet);

        this.editParticipant.getStylesheets().remove(contrastStyleSheet);
        this.editParticipant.getStylesheets().add(styleSheet);

        this.inviteSend.getStylesheets().remove(contrastStyleSheet);
        this.inviteSend.getStylesheets().add(styleSheet);

        this.debt.getStylesheets().remove(contrastStyleSheet);
        this.debt.getStylesheets().add(styleSheet);

        this.debtOverview.getStylesheets().remove(contrastStyleSheet);
        this.debtOverview.getStylesheets().add(styleSheet);

        this.adminLogin.getStylesheets().remove(contrastStyleSheet);
        this.adminLogin.getStylesheets().add(styleSheet);

        this.adminPage.getStylesheets().remove(contrastStyleSheet);
        this.adminPage.getStylesheets().add(styleSheet);

        this.editExpensePage.getStylesheets().remove(contrastStyleSheet);
        this.editExpensePage.getStylesheets().add(styleSheet);

        this.giveMoneyPage.getStylesheets().remove(contrastStyleSheet);
        this.editExpensePage.getStylesheets().add(styleSheet);
    }

    /**
     * Method that opens the Give Money Page.
     * */
    public void showGiveMoneyPage() {
        primaryStage.setTitle("Give Money");
        giveMoneyCtrl.updatePage();
        primaryStage.setScene(giveMoneyPage);

    }
}