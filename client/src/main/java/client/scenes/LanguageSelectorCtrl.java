package client.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class LanguageSelectorCtrl {
    private final MainCtrl mainCtrl;

    @FXML // fx:id="languageComboBox"
    private ComboBox<?> languageComboBox;

    @Inject
    public LanguageSelectorCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert languageComboBox != null : "fx:id=\"languageComboBox\" was not injected: check your FXML file 'AddExpense.fxml'.";
    }
}

