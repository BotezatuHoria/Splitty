package client.scenes;

import client.Config;
import client.Main;
import client.utils.FlagListCell;
import client.utils.LanguageSingleton;
import client.utils.ServerUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ResourceBundle;

public class StartSettingsCtrl {
    private final MainCtrl mainCtrl;
    private static Config config = ServerUtils.getConfig();
    @FXML
    public Label serverLabel;
    @FXML
    public TextField serverTextField;
    @FXML
    public Button startPageConfirm;

    @FXML // fx:id="startSettingsLabel"
    private Text startSettingsLabel;

    @FXML // fx:id="startPageAdmin"
    private Button startPageAdmin;

    @FXML // fx:id="startPageLanguageSelector"
    private ComboBox startPageLanguageSelector; // Value injected by FXMLLoader

    @FXML
    private Button changeButton;

    @FXML
    private Button okButton;

    @FXML
    private Label infoLabel;

    @FXML
    private Label changeLabel;

    @FXML
    private Button downloadButton;


    /**
     * Constructor for the statistics controller.
     * @param mainCtrl - reference to the main controller.
     */
    @Inject
    public StartSettingsCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    void initializeLanguages() {
        // Push all the languages to the combobox
        startPageLanguageSelector.getItems().addAll(FlagListCell.getLanguages());

        // Responsible for setting the flags and changing languages
        startPageLanguageSelector.setCellFactory(lv -> new FlagListCell());
        startPageLanguageSelector.setButtonCell(new FlagListCell());

        startPageLanguageSelector.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                LanguageSingleton.getInstance().setLanguage((Pair<String, Image>) newVal);
                LanguageSingleton.getInstance().setLanguageText();

                Config config = ServerUtils.getConfig();
                if (config != null) {
                    config.setLanguage(((Pair<String, Image>) newVal).getKey());
                    ServerUtils.setConfig(config);
                }
            }
        });

        // Show current language
        Pair<String, Image> currentLanguage = LanguageSingleton.getInstance().getLanguage();
        startPageLanguageSelector.getSelectionModel().select(currentLanguage);
    }

    public void setLanguageSelector() {
        Pair<String, Image> currentLanguage = LanguageSingleton.getInstance().getLanguage();
        startPageLanguageSelector.getSelectionModel().select(currentLanguage);
    }

    public void setLanguageText(ResourceBundle resourceBundle) {
        startSettingsLabel.setText(resourceBundle.getString("select.language"));
        downloadButton.setText(resourceBundle.getString("button.downloadTemplate"));
    }

    @FXML
    void adminLogin(){
        mainCtrl.showAdminLogin();
    }

    @FXML
    void showStart() {
        mainCtrl.showStarter();
    }


    public void changeServer() {
        serverLabel.visibleProperty().set(false);
        serverTextField.visibleProperty().set(true);
        serverTextField.setText(serverLabel.getText());
        changeButton.visibleProperty().set(false);
        okButton.visibleProperty().set(true);
    }

    public void confirmServer() throws IOException {
        responseServer();
        serverLabel.setText(serverTextField.getText().trim());
        ServerUtils.setServer(serverLabel.getText().trim());
        serverTextField.visibleProperty().set(false);
        serverLabel.visibleProperty().set(true);
        changeButton.visibleProperty().set(true);
        okButton.visibleProperty().set(false);
    }

    /**
     * makes info text about importing the template file appear.
     */
    public void showInfo(){
        infoLabel.setText(LanguageSingleton.getInstance().getResourceBundle().getString("info.import.template"));
    }

    /**
     * the info-text disappear again.
     */
    public void notShowInfo(){
        infoLabel.setText("");
    }

    /**
     * imports the template to a location the user can choose.
     * @throws IOException When the file to download is not found.
     */
    public void downloadTemplate(){
        File file = new File("client/src/main/resources/template.properties"); // needs to be edited to correct template!!!!
        Window parent = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("languageTemplate.properties");
        //fileChooser.setSelectedFile(new File("languageTemplate.properties"));
        try{
            File saveFile = fileChooser.showSaveDialog(parent);
            try (FileInputStream in = new FileInputStream(file); FileOutputStream out = new FileOutputStream(saveFile)) {

                int n;

                // read() function to read the
                // byte of data
                while ((n = in.read()) != -1) {
                    // write() function to write
                    // the byte of data
                    out.write(n);
                }
            } finally {
                // close() function to close the
                // stream
                // close() function to close
                // the stream
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText(LanguageSingleton.getInstance().getResourceBundle().getString("alert.template.download.success"));
                alert.showAndWait();
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void responseServer() throws IOException {
        Main main = new Main();
        String host = serverTextField.getText();
        boolean canWeConnect = main.checkConnection(host);
        if (!(canWeConnect)){
            changeLabel.setText(LanguageSingleton.getInstance().getResourceBundle().getString("error.incorrectServerInput"));
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(LanguageSingleton.getInstance().getResourceBundle().getString("alert.serverIncorrect"));
            alert.showAndWait();
            startPageConfirm.setDisable(true);
            downloadButton.setDisable(true);
            startPageAdmin.setDisable(true);
        }
        else{
            Alert alertb = new Alert(Alert.AlertType.INFORMATION);
            alertb.setContentText(LanguageSingleton.getInstance().getResourceBundle().getString("alert.serverConnectSuccess"));
            alertb.showAndWait();
            changeLabel.setText("");
            startPageConfirm.setDisable(false);
            downloadButton.setDisable(false);
            startPageAdmin.setDisable(false);

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
            File file = new File(path);
            var fileReader = new FileReader(file);
            ObjectMapper objectMapper = new ObjectMapper();
            config = objectMapper.readValue(fileReader, Config.class);
            String oldHost = config.getClientsServer();
            config.setServer(host);
            System.out.println("config file, server overwritten from " + oldHost + " to "+ config.getClientsServer());
        }
    }
}
