package client.scenes;

import client.utils.FlagListCell;
import client.utils.LanguageSingleton;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import org.apache.commons.lang3.tuple.Pair;

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
}
