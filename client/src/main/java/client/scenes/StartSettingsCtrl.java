package client.scenes;

import client.utils.FlagListCell;
import client.utils.LanguageSingleton;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import org.apache.commons.lang3.tuple.ImmutablePair;
import javafx.scene.image.Image;
import org.apache.commons.lang3.tuple.Pair;

public class StartSettingsCtrl {
    private final MainCtrl mainCtrl;
    @FXML // fx:id="startPageConfirmButton"
    private Button startPageConfirmButton;

    @FXML // fx:id="startPageAdmin"
    private Button startPageAdmin;
    @FXML // fx:id="startPageLanguageSelector"
    private ComboBox startPageLanguageSelector; // Value injected by FXMLLoader

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
        Pair<String, Image> currentLanguage = (Pair<String, Image>) startPageLanguageSelector.getItems().stream()
            .filter(lang ->  lang.equals((Pair<String, Image>) LanguageSingleton.getInstance().getLanguage()))
            .findFirst()
            .orElse(null);

        startPageLanguageSelector.getSelectionModel().select(currentLanguage);

        startPageLanguageSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                LanguageSingleton instance = LanguageSingleton.getInstance();
                instance.setLanguage((Pair<String, Image>) newValue);
            }
        });
    }

    @FXML
    void adminLogin(){
        mainCtrl.showAdminLogin();
    }

    @FXML
    void showStart() {
        mainCtrl.showStarter();
    }

    @FXML
    void selectLanguage() {

    }
}
