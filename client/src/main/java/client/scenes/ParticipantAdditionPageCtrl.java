/**
 * Sample Skeleton for 'ParticipantAdditionPage.fxml' Controller Class
 */

package client.scenes;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ParticipantAdditionPageCtrl {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="abortButton"
    private Button abortButton; // Value injected by FXMLLoader

    @FXML // fx:id="createButton"
    private Button createButton; // Value injected by FXMLLoader

    @FXML // fx:id="email"
    private TextField email; // Value injected by FXMLLoader

    @FXML // fx:id="firstName"
    private TextField firstName; // Value injected by FXMLLoader

    @FXML // fx:id="iban"
    private TextField iban; // Value injected by FXMLLoader

    @FXML // fx:id="lastName"
    private TextField lastName; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert abortButton != null : "fx:id=\"abortButton\" was not injected: check your FXML file 'ParticipantAdditionPage.fxml'.";
        assert createButton != null : "fx:id=\"createButton\" was not injected: check your FXML file 'ParticipantAdditionPage.fxml'.";
        assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'ParticipantAdditionPage.fxml'.";
        assert firstName != null : "fx:id=\"firstName\" was not injected: check your FXML file 'ParticipantAdditionPage.fxml'.";
        assert iban != null : "fx:id=\"iban\" was not injected: check your FXML file 'ParticipantAdditionPage.fxml'.";
        assert lastName != null : "fx:id=\"lastName\" was not injected: check your FXML file 'ParticipantAdditionPage.fxml'.";

    }

}
