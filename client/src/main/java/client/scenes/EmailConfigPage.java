package client.scenes;

import client.Config;
import client.utils.ServerUtils;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EmailConfigPage {

    @FXML
    private Button closeButton;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailTextField;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button saveButton;

    @FXML
    private Label titleConfig;

    private final MainCtrl mainCtrl;

    @Inject
    public EmailConfigPage(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    public void goBack(ActionEvent event) {
        mainCtrl.showStartSettings();
    }

   public void save(ActionEvent event) {
        Config config = ServerUtils.getConfig();
        if (config != null) {
            config.setEmailAddress(emailTextField.getText().trim());
            config.setPassword(passwordTextField.getText().trim());
            ServerUtils.setConfig(config);
        }
    }

}
