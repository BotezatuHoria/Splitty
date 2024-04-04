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

import java.io.*;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;



import client.scenes.*;
import client.utils.ServerUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Injector;

//import client.scenes.AddQuoteCtrl;
//import client.scenes.QuoteOverviewCtrl;

import jakarta.ws.rs.BadRequestException;
import javafx.application.Application;
import javafx.stage.Stage;

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

    @Override
    public void start(Stage primaryStage) throws IOException {

        getConfigFile();
        String host = config.getClientsServer();
        boolean result = checkConnection(host); //if this is false, the client is trying to connect to a server that is not running.
        // we can choose to have ui for this or not...

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
        var startSettings = FXML.load(StartSettingsCtrl.class, "client", "scenes", "StartSettings.fxml");
        var editParticipants = FXML.load(ParticipantEditPageCtrl.class, "client", "scenes",
                "ParticipantEditPage.fxml");
        var editExpensePage = FXML.load(EditExpenseCtrl.class, "client", "scenes", "EditExpense.fxml");

        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        mainCtrl.initialize(primaryStage, starterPage, eventPage, statisticsPage, expensePage, startSettings,
                addParticipants, editParticipants, inviteSend, debtPage, languageSelector,adminLogin, adminPage, editExpensePage);

        primaryStage.setOnCloseRequest(e -> {
            eventPage.getKey().stop();
        });
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
            ServerUtils.setServer(config.getClientsServer());
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file: " + e.getMessage());
        }
    }

    /**
     * checks whether the server the client wants to connect to is running (tested with an api call).
     * @return true when client connects to running server, false otherwise.
     */
    public boolean checkConnection(String host){
        String uri = host + "/api/events";
        URL url;
        try {
            url = new URI(uri).toURL();
            var is = url.openConnection().getInputStream();
            var br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }  catch (BadRequestException e) {
            //if it is a bad request, then apparently there is a connection with the server (because they responded this)
            // therefore connection is true.
            return true;
        }
        catch (ConnectException e) {
            //no connection, wrong port.
            return false;
        }
        catch (Exception e) {
            //connection but file not found, does not matter, so still true.
            return true;
        }
        return true;


    }
}