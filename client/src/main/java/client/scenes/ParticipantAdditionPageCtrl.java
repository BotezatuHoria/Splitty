package client.scenes;

import java.net.URL;
import java.util.ResourceBundle;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Person;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

//TODO: Check the user input to be valid before creating a new user. Maybe not in this class?
//TODO: fix the event id so that it is passed from the event controller.
//TODO: Create the Create method after the server.utils is created.

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

    @FXML
    private Label firstnameResponse;

    @FXML
    private Label lastnameResponse;


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
     * TODO: fix the event reference so that it is passed from the event controller.
     * @return returns the created person
     */
    private Person getPerson(){
        Person p = new Person(email.getText(), firstName.getText(), lastName.getText(), iban.getText(), new Event(),
                null, null);
        return p;
    }

    /**
     * Method for the create button, to add the person to the participant list.
     */
    public void create(){
        int amountOfPerson = server.getPeopleInCurrentEvent(mainCtrl.getCurrentEventID()).size();
        try {
            createPerson();
        } catch (WebApplicationException e) {

            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }


        if( server.getPeopleInCurrentEvent(mainCtrl.getCurrentEventID()).size()> amountOfPerson )    {
            clearFields();
            mainCtrl.showEventPage(mainCtrl.getCurrentEventID());  //This method still needs to be created
        }
    }

    /**
     * Method for the abort button.
     */
    public void abort(){
        clearFields();
        mainCtrl.showEventPage(mainCtrl.getCurrentEventID());
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
     * Method that uses user keyboard input as button input.
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


    /**
     * creates a person, or when the values are invalid, points out which fields are yet to be filled in (and required to).
     */
    public void createPerson() {
        String newFirstName = firstName.getText().trim();
        String newLastName = lastName.getText().trim();
        String newEmail = email.getText().trim();
        String newIban = iban.getText().trim();
        if(newFirstName.equals("") && newLastName.equals("") ){
            firstnameResponse.setText("Cannot be empty! Fill in this field!");
            firstnameResponse.setStyle("-fx-font-style: italic");
//            firstnameResponse.setStyle("-fx-background-color: red");
//            firstName.setStyle("-fx-background-color: red");

            lastnameResponse.setText("Cannot be empty! Fill in this field!");
            lastnameResponse.setStyle("-fx-font-style: italic");
//            lastnameResponse.setStyle("-fx-background-color: red");
//            lastName.setStyle("--body-background-color: rgba(175,52,52,0.6)");
        }
        if(newFirstName.equals("") ){
            firstnameResponse.setText("Cannot be empty! Fill in this field!");
            firstName.setStyle("--body-background-color: rgba(175,52,52,0.6)");
        }
        else if(newLastName.equals("")){
            lastnameResponse.setText("Cannot be empty! Fill in this field!");
            lastName.setStyle("--body-background-color: rgba(175,52,52,0.6)");
        }
        else{
            Person person = new Person(newEmail, newFirstName, newLastName, newIban,
                    null, null, null);
            Person thePerson = server.addPerson(person, mainCtrl.getCurrentEventID());
        }

    }

    /**
     * resets the warnings given when someone tries to add a person without a name or a surname.
     */
    public void resetWarnings(){
        firstnameResponse.setText("");
        lastnameResponse.setText("");
        // also reset the colors of the firstName bars etc if they changed after an error (wanted to implement that).

    }
}
