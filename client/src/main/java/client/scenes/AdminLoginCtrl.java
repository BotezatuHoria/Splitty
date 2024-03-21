package client.scenes;

import client.utils.ServerUtils;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import java.awt.event.ActionEvent;

public class AdminLoginCtrl {
    private final MainCtrl mainCtrl;
    private final ServerUtils serverUtils;

    @FXML
    private Button backButton;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    Alert a = new Alert(Alert.AlertType.NONE);


    void initialize(){
        assert backButton != null;
        assert loginButton != null;
        assert passwordField != null;
    }

    /**
     * Constructor for the admin login controller
     * @param server the server
     * @param mainCtrl the main controller
     */
    @Inject
    public AdminLoginCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.serverUtils = server;
        this.mainCtrl = mainCtrl;
    }

    public void loginButton_onClick(ActionEvent e){
        String password = passwordField.getText();
        String correctPassword = serverUtils.getPassword();
        if (password.equals(correctPassword)){
            //go to the admin page
        } else {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Wrong Password!\nPlease look in the server console for the correct password");
        }
    }
}
