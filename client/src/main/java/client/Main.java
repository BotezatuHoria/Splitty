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
package client;

import static com.google.inject.Guice.createInjector;

import java.io.IOException;
import java.net.URISyntaxException;

import client.scenes.*;
import com.google.inject.Injector;

//import client.scenes.AddQuoteCtrl;
//import client.scenes.QuoteOverviewCtrl;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    /**
     * Main function for the Main class.
     * @param args - arguments
     * @throws URISyntaxException - Syntax exception
     * @throws IOException - io exception
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        var starterPage = FXML.load(StarterPageCtrl.class, "client", "scenes", "StarterPage.fxml");
        var eventPage = FXML.load(EventPageCtrl.class, "client", "scenes", "EventPage.fxml");
        var statisticsPage = FXML.load(StatisticsCtrl.class, "client", "scenes", "Statistics.fxml");
        var expensePage = FXML.load(AddExpenseCtrl.class, "client", "scenes", "AddExpense.fxml");
        var languageSelector = FXML.load(LanguageSelectorCtrl.class, "client", "scenes", "LanguageSelector.fxml");
        var inviteSend = FXML.load(InviteSendingCtrl.class, "client", "scenes", "InviteSending.fxml");
        var debtPage = FXML.load(DebtSettlementCtrl.class, "client", "scenes", "DebtsSettlementPage.fxml");
        var adminLogin = FXML.load(AdminLoginCtrl.class, "client", "scenes", "AdminLogin.fxml");
        var adminPage = FXML.load(AdminPageCtrl.class, "client", "scenes", "AdminPage.fxml");
        var addParticipants = FXML.load(ParticipantAdditionPageCtrl.class, "client", "scenes",
                "ParticipantAdditionPage.fxml");
        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        mainCtrl.initialize(primaryStage, starterPage, eventPage, statisticsPage, expensePage,
                addParticipants, inviteSend, debtPage, languageSelector,adminLogin, adminPage);
    }
}