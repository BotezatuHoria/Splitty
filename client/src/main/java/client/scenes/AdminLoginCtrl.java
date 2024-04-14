package client.scenes;

import client.utils.LanguageSingleton;
import client.utils.ServerUtils;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminLoginCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ServerUtils serverUtils;

    @FXML
    private Button backButton;

    @FXML
    private Label adminLoginLabel;

    @FXML
    private Label adminPasswordLabel;

    @FXML
    private Label passwordConsoleLabel;

    @FXML
    private Label resendPasswordLabel;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button resendButton;

    Alert a = new Alert(Alert.AlertType.NONE);


    /**
     * initializes and checks whether the fields are not null.
     */
    void initialize(){
        assert backButton != null;
        assert loginButton != null;
        assert passwordField != null;
        assert resendButton != null;
    }

    /**
     * Constructor for the admin login controller.
     * @param server the server
     * @param mainCtrl the main controller
     */
    @Inject
    public AdminLoginCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.serverUtils = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * makes it so that when the 'login' button is clicked the program continues .
     * it checks whether the password is correct, and if so sends the admin through to the page.
     */
    public void loginButton_onClick(){
        String password = passwordField.getText();
        String correctPassword = serverUtils.getPassword();
        if (password.equals(correctPassword)){
            mainCtrl.showAdminPage();
        } else {
            a.setAlertType(Alert.AlertType.ERROR);
            String passwordString = LanguageSingleton.getInstance().getResourceBundle().getString("admin.login.password.error");
            a.setContentText(passwordString);
            a.show();
        }
        passwordField.clear();
    }

    /**
     * sets all the text in the file to the correct language chosen by the user.
     * @param resourceBundle the language.
     */
    public void setLanguageText(ResourceBundle resourceBundle) {
        adminLoginLabel.setText(resourceBundle.getString("admin.login.label"));
        adminPasswordLabel.setText(resourceBundle.getString("admin.password.label"));
        passwordConsoleLabel.setText(resourceBundle.getString("password.console.label"));
        resendPasswordLabel.setText(resourceBundle.getString("resend.password.label"));
        resendButton.setText(resourceBundle.getString("resend.password.button"));
        loginButton.setText(resourceBundle.getString("login.button"));
    }

    /**
     * Relogs the password in the server console.
     */
    public void resendPassword(){
        serverUtils.sendPassword();
    }

    /**
     * button function that leads you back to the settings page.
     */
    public void goBack() {
        mainCtrl.showStartSettings();
    }

    @Override
    /**
     * initializes the buttons with their functions.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainCtrl.handleEnterKeyPress(backButton, this::goBack);
        mainCtrl.handleEnterKeyPress(loginButton, this::loginButton_onClick);
        mainCtrl.handleEnterKeyPress(resendButton, this::resendPassword);
    }
}
