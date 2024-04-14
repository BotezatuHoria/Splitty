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

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;


import client.scenes.*;
import client.utils.LanguageSingleton;
import client.utils.ServerUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Injector;

//import client.scenes.AddQuoteCtrl;
//import client.scenes.QuoteOverviewCtrl;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.glassfish.jersey.client.ClientConfig;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    private static Config config = new Config();


    /**
     * Main function for the Main class.
     * @param args - arguments
     * @throws URISyntaxException - Syntax exception
     * @throws IOException - io exception
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    /**
     *
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        getConfigFile();
        String host = config.getClientsServer();
        boolean result = checkConnection(host);

        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);

        LanguageSingleton languageSingleton = LanguageSingleton.getInstance();
        languageSingleton.setMainCtrl(mainCtrl);

        var starterPage = FXML.load(StarterPageCtrl.class, "client", "scenes", "StarterPage.fxml");
        var eventPage = FXML.load(EventPageCtrl.class, "client", "scenes", "EventPage.fxml");
        var statisticsPage = FXML.load(StatisticsCtrl.class, "client", "scenes", "Statistics.fxml");
        var expensePage = FXML.load(AddExpenseCtrl.class, "client", "scenes", "AddExpense.fxml");
        var inviteSend = FXML.load(InviteSendingCtrl.class, "client", "scenes", "InviteSending.fxml");
        var debtPage = FXML.load(DebtSettlementCtrl.class, "client", "scenes", "DebtsSettlementPage.fxml");
        var adminLogin = FXML.load(AdminLoginCtrl.class, "client", "scenes", "AdminLogin.fxml");
        var adminPage = FXML.load(AdminPageCtrl.class, "client", "scenes", "AdminPage.fxml");
        var addParticipants = FXML.load(ParticipantAdditionPageCtrl.class, "client", "scenes",
                "ParticipantAdditionPage.fxml");
        var startSettings = FXML.load(StartSettingsCtrl.class, "client", "scenes", "StartSettings.fxml");
        var editParticipants = FXML.load(ParticipantEditPageCtrl.class, "client", "scenes",
                "ParticipantEditPage.fxml");
        var editExpensePage = FXML.load(EditExpenseCtrl.class, "client", "scenes", "EditExpense.fxml");

        var debtOverviewPage = FXML.load(DebtOverviewPageCtrl.class, "client", "scenes", "DebtOverviewPage.fxml");
        var giveMoneyPage = FXML.load(GiveMoneyCtrl.class, "client", "scenes", "GiveMoney.fxml");

        mainCtrl.initialize(primaryStage, starterPage, eventPage, statisticsPage, expensePage, startSettings,
                addParticipants, editParticipants, inviteSend, debtPage,adminLogin, adminPage, editExpensePage, debtOverviewPage, giveMoneyPage);

        primaryStage.setOnCloseRequest(e -> {
            eventPage.getKey().stop();
        });

        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/Splitty.png")));

        if (Taskbar.isTaskbarSupported()) {
            var taskbar = Taskbar.getTaskbar();

            if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
                var dockIcon = defaultToolkit.getImage(getClass().getResource("/images/Splitty.png"));
                taskbar.setIconImage(dockIcon);
            }

        }
    }

    /**
     * gets the values from the config file, and sets the server the client wants to connect to.
     * this is the server that the client put in the config file.
     * future use; language remembering? email sending?.
     * @throws IOException when file path is incorrect.
     */
    public void getConfigFile() throws IOException {
        String path= "";
        try {
            path = Main.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()
                    .getPath();
            path = path + "/client/config.json";
        } catch (URISyntaxException ex) {
            System.out.println("URISyntaxException: " + ex.getMessage());
        }

        try {
            var file = new File(path);
            if (file.createNewFile()) {
                System.out.println("File created successfully!");
                FileWriter writer = new FileWriter(path);
                writer.write("{\n" +
                        "  \"server\": \"http://localhost:8080/\",\n" +
                        "  \"emailAddress\": \"example@gmail.com\",\n" +
                        "  \"language\": \"en\"\n" +
                        "}");
                writer.close();
            } else {
                System.out.println("File already exists at the specified location.");
            }
            var fileReader = new FileReader(file);
            ObjectMapper objectMapper = new ObjectMapper();
            config = objectMapper.readValue(fileReader,Config.class);
            ServerUtils.setConfig(config);
            ServerUtils.setServer(config.getClientsServer());
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file: " + e.getMessage());
        }
    }

    /**
     * checks whether the server the client wants to connect to is running (tested with an api call).
     * @return true when client connects to running server, false otherwise.
     */

    public boolean checkConnection(String host) {
        if(host.length() > 22){
            return false;
        }

        try {
            Response response = ClientBuilder.newClient(new ClientConfig()) //
                    .target(host)
                    .request().get();
            System.out.println(response.getStatus());
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}