package client.scenes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import client.utils.LanguageSingleton;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Person;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

//TODO: Check the user input to be valid before creating a new user. Maybe not in this class?
//TODO: fix the event id so that it is passed from the event controller.
//TODO: Create the Create method after the server.utils is created.

public class ParticipantAdditionPageCtrl implements Initializable {

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
    private Label participantAdditionLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label lastnameResponse;

    @FXML
    private Label doublePersonResponse;


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
        Person p = new Person(email.getText(), firstName.getText(), lastName.getText(), iban.getText());
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(LanguageSingleton.getInstance().getResourceBundle().getString("participant.add.success"));
            alert.showAndWait();

            clearFields();
            mainCtrl.showEventPage(mainCtrl.getCurrentEventID());  //This method still needs to be created
        }
        else {
        System.out.println("CREATING FAILED: Person not added, fields were empty or person already existed in the event");
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
        String newFirstName = firstName.getText().trim();                   //get all the values of the filled-in fields.
        String newLastName = lastName.getText().trim();
        String newEmail = email.getText().trim();
        String newIban = iban.getText().trim();
        boolean isDuplicate = personExists(newFirstName,newLastName);

        List<Person> allPersons = server.getPeopleInCurrentEvent(mainCtrl.getCurrentEventID());

        firstnameResponse.setText(LanguageSingleton.getInstance().getResourceBundle().getString("error.emptyField"));     //as standard, there are warnings, they go away when not applicable.
        firstnameResponse.setStyle("-fx-font-style: italic");
        lastnameResponse.setText(LanguageSingleton.getInstance().getResourceBundle().getString("error.emptyField"));
        lastnameResponse.setStyle("-fx-font-style: italic");

        if(newFirstName.isEmpty() && newLastName.isEmpty()){
            return;
        }
        // maybe if possible color the textfield red when this error occurs.
        else if(newFirstName.isEmpty()){
            lastnameResponse.setText("");
        }
        else if(newLastName.isEmpty()){
            firstnameResponse.setText("");
        }
        else if (isDuplicate) {
            firstnameResponse.setText(LanguageSingleton.getInstance().getResourceBundle().getString("error.duplicatePerson"));
            lastnameResponse.setText("");
            doublePersonResponse.setText(LanguageSingleton.getInstance().getResourceBundle().getString("error.personExists"));
            System.out.println("EDITING FAILED: User tried to add a person already existing in this event (by first + lastname)");
        }
        else {
            firstnameResponse.setText("");
            lastnameResponse.setText("");
            Person person = new Person(newEmail, newFirstName, newLastName, newIban);
            Person thePerson = server.addPerson(person, mainCtrl.getCurrentEventID());
        }
        System.out.println("ADDING SUCCESS: Person added to the event");
    }


    /**
     * checks wheter the edited participant has a first and lastname that already exist in event. will be used to shorten the above method to 50< lines.
     * @param firstname the new firstname of person.
     * @param lastname the new lastname of person.
     * @return true if another person with same names exist.
     */
    public boolean personExists(String firstname, String lastname) {
        List<Person> allPersons = server.getPeopleInCurrentEvent(mainCtrl.getCurrentEventID());
        boolean personIsDuplicate = false;
        for (Person e : allPersons) {
            if (firstname.equals(e.getFirstName()) && lastname.equals(e.getLastName())) {
                doublePersonResponse.setText(LanguageSingleton.getInstance().getResourceBundle().getString("error.personExists"));
                System.out.println("ADDING ERROR: user tried to add a combination of firstname and lastname that already exists");
                personIsDuplicate = true;
            }
        }
        return personIsDuplicate;
    }

    public void setLanguageText(ResourceBundle resourceBundle) {
        participantAdditionLabel.setText(resourceBundle.getString("participant.form.title"));
        nameLabel.setText(resourceBundle.getString("participant.form.label.name"));
        firstName.setPromptText(resourceBundle.getString("participant.form.placeholder.firstName")); // Assuming these are text fields with placeholders
        lastName.setPromptText(resourceBundle.getString("participant.form.placeholder.lastName"));
        abortButton.setText(resourceBundle.getString("participant.form.button.abort"));
    }

    /**
     * resets the warnings given when someone tries to add a person without a name or a surname.
     */
    public void resetWarnings(){
        firstnameResponse.setText("");
        lastnameResponse.setText("");
        doublePersonResponse.setText("");
        // also reset the colors of the firstName bars etc if they changed after an error (wanted to implement that).

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
