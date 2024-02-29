package client.scenes;

import java.net.URL;
import java.util.ResourceBundle;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Person;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import commons.PersonTemporary;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

public class ParticipantAdditionPageCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

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

    /**
     * Constructor for the participant addition page controller.
     * @param server the server
     * @param mainCtrl the main controller
     */
    @Inject
    public ParticipantAdditionPageCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * TODO: fix the event id so that it is passed from the event controller.
     * @return returns the created person
     */
    private PersonTemporary getPerson(){
        PersonTemporary p = new PersonTemporary(email.getText(), firstName.getText(), lastName.getText(), iban.getText(), 1);
        return p;
    }

    /**
     * Method for the create button.
     * TODO: Create this method after the server.utils is created.
     */
    public void create(){

    }

    /**
     * Method for the abort button.
     */
    public void abort(){
        clearFields();
        mainCtrl.showOverview();
    }

    /**
     * Clears all the input fields.
     */
    private void clearFields() {
        email.clear();
        firstName.clear();
        lastName.clear();
        iban.clear();
    }

    /**
     * Method that uses user keyboard input as button input
     * @param e the key that was pressed
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                create();
                break;
            case ESCAPE:
                abort();
                break;
            default:
                break;
        }
    }

}
