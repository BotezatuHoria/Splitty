package client.scenes;

import client.utils.FlagListCell;
import client.utils.LanguageSingleton;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.io.*;
import java.util.ResourceBundle;

public class StartSettingsCtrl {
    private final MainCtrl mainCtrl;
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


    /**
     * Constructor for the statistics controller.
     * @param mainCtrl - reference to the main controller.
     */
    @Inject
    public StartSettingsCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        // Push all the languages to the combobox
        startPageLanguageSelector.getItems().addAll(FlagListCell.getLanguages());

        // Responsible for setting the flags and changing languages
        startPageLanguageSelector.setCellFactory(lv -> new FlagListCell());
        startPageLanguageSelector.setButtonCell(new FlagListCell());

        // Show current language
        Pair<String, Image> currentLanguage = LanguageSingleton.getInstance().getLanguage();
        startPageLanguageSelector.getSelectionModel().select(currentLanguage);
    }

    public void setLanguageText(ResourceBundle resourceBundle) {
        startSettingsLabel.setText(resourceBundle.getString("select.language"));
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

    public void confirmServer() {
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
        infoLabel.setText("Import a template which you can use to add a language. Sent this filled template to the moderators of the app, group53@gmail.com");
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
    public void importTemplate() throws IOException {
        File file = new File("client/src/main/resources/messages_en.properties"); // needs to be edited to correct template!!!!
        JFrame parent = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("languageTemplate.properties"));

        try{

            int returnValue = fileChooser.showOpenDialog(parent);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File saveFile = fileChooser.getSelectedFile();
                FileInputStream in = new FileInputStream(file);
                FileOutputStream out = new FileOutputStream(saveFile);
                try {

                    int n;

                    // read() function to read the
                    // byte of data
                    while ((n = in.read()) != -1) {
                        // write() function to write
                        // the byte of data
                        out.write(n);
                    }
                } finally {
                    if (in != null) {

                        // close() function to close the
                        // stream
                        in.close();
                    }
                    // close() function to close
                    // the stream
                    if (out != null) {
                        out.close();
                    }
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Downloading of the template file succeeded!");
                    alert.showAndWait();
                }
            }

        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
