package client.scenes;

import client.utils.FlagListCell;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import org.apache.commons.lang3.tuple.ImmutablePair;
import javafx.scene.image.Image;

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
        startPageLanguageSelector.getItems().addAll(FlagListCell.getLanguages());

        startPageLanguageSelector.setCellFactory(lv -> new FlagListCell());
        startPageLanguageSelector.setButtonCell(new FlagListCell());
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
