package client.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class LanguageSelectorCtrl {
    private final MainCtrl mainCtrl;

    @FXML // fx:id="languageComboBox"
    private ComboBox<?> languageComboBox;

    @FXML
    private Button backButton;

    /**
     * Constructor for the language selector controller.
     * @param mainCtrl - reference to the main controller
     */
    @Inject
    public LanguageSelectorCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert languageComboBox != null : "fx:id=\"languageComboBox\" was not injected: check your FXML file 'AddExpense.fxml'.";
    }

    /**
     * Method that goes back to the home page.
     */
    public void goHome() {
        mainCtrl.showStarter();
    }
}

