package client.scenes;

import client.utils.ServerUtils;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

public class AdminLoginCtrl {
    private final MainCtrl mainCtrl;
    private final ServerUtils serverUtils;

    @FXML
    private Button backButton;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button resendPassword;

    Alert a = new Alert(Alert.AlertType.NONE);


    /**
     * initializes and checks whether the fields are not null.
     */
    void initialize(){
        assert backButton != null;
        assert loginButton != null;
        assert passwordField != null;
        assert resendPassword != null;
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
            a.setContentText("Wrong Password!\nPlease look in the server console for the correct password");
            a.show();
        }
        passwordField.clear();
    }

    /**
     * Relogs the password in the server console.
     */
    public void resendPassword(){
        serverUtils.sendPassword();
    }

    public void goBack() {
        mainCtrl.showStartSettings();
    }
}
