package client.scenes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ParticipantEditPageCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button abortButton;

    @FXML
    private TextField email;

    @FXML
    private TextField firstName;

    @FXML
    private TextField iban;

    @FXML
    private TextField lastName;

    @FXML
    private Label originalEmail;

    @FXML
    private Label originalFName;

    @FXML
    private Label originalIBAN;

    @FXML
    private Label originalLName;

    @FXML
    private ComboBox<Person> participantsScroll;

    @FXML
    private Button saveButton;

    @FXML
    private Label warningLabel;


    @FXML
    void initialize() {
        assert abortButton != null : "fx:id=\"abortButton\" was not injected: check your FXML file 'ParticipantEditPage.fxml'.";
        assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'ParticipantEditPage.fxml'.";
        assert firstName != null : "fx:id=\"firstName\" was not injected: check your FXML file 'ParticipantEditPage.fxml'.";
        assert iban != null : "fx:id=\"iban\" was not injected: check your FXML file 'ParticipantEditPage.fxml'.";
        assert lastName != null : "fx:id=\"lastName\" was not injected: check your FXML file 'ParticipantEditPage.fxml'.";
        assert originalEmail != null : "fx:id=\"originalEmail\" was not injected: check your FXML file 'ParticipantEditPage.fxml'.";
        assert originalFName != null : "fx:id=\"originalFName\" was not injected: check your FXML file 'ParticipantEditPage.fxml'.";
        assert originalIBAN != null : "fx:id=\"originalIBAN\" was not injected: check your FXML file 'ParticipantEditPage.fxml'.";
        assert originalLName != null : "fx:id=\"originalLName\" was not injected: check your FXML file 'ParticipantEditPage.fxml'.";
        assert participantsScroll != null : "fx:id=\"participantsScroll\" was not injected: check your FXML file 'ParticipantEditPage.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'ParticipantEditPage.fxml'.";

    }


    /**
     * Constructor for the participant addition page controller.
     *
     * @param server   the server
     * @param mainCtrl the main controller
     */
    @Inject
    public ParticipantEditPageCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }


    /**
     * Method that updates the title, people and transactions on that page.
     */
    public void updatePage() {
        displayParticipants();
    }

    /**
     * Displays participants on that page for the current event.
     */
    private void displayParticipants() {
        List<Person> people = server.getPeopleInCurrentEvent(mainCtrl.getCurrentEventID());
        participantsScroll.getItems().clear();
        for (Person person : people) {
            participantsScroll.getItems().add(person);
        }
        System.out.println(participantsScroll.getItems());
    }

    /**
     * is the selector of the dropdown menu of participants of this event.
     */
    public void selectParticipant() {
        Person person = participantsScroll.getSelectionModel().getSelectedItem();
        System.out.println("person to edit: " + person);
        if (person != null) {
            originalFName.setText(person.getFirstName());
            originalLName.setText(person.getLastName());
            originalEmail.setText(person.getEmail());
            originalIBAN.setText(person.getIban());
        }
    }

    /**
     * should save the adjusted person to the db.
     */
    public void saveUpdatedParticipant() {
        Person person = participantsScroll.getSelectionModel().getSelectedItem();

        String newFirstName = firstName.getText();
        String newLastName = lastName.getText();
        String newEmail = email.getText();
        String newIban = iban.getText();

        if (newFirstName.isEmpty() || newLastName.isEmpty()) {
            warningLabel.setText("The firstname and/or lastname column are empty; " +
                    "it is mandatory to give the person a name. Please fill these in.");
        }
        if (!(personExists(newFirstName, newLastName))) {
            person.setFirstName(newFirstName);
            person.setLastName(newLastName);
            person.setEmail(newEmail);
            person.setIban(newIban);
            server.updatePerson(person.getId(), person);
            System.out.println("THIS PART NOT YET DONE, FIX API");
            System.out.println("person with id " + person.getId() + " was adjusted to " + person);
            clearFields();
            mainCtrl.showEventPage(mainCtrl.getCurrentEventID());
        } else {
            if(!(personExists(newFirstName,newLastName))){
                warningLabel.setText("Something went wrong. Please contact t.p.p.vanleest@student.tudelft.nl");
            }
        }


    }

    /**
     * checks wheter the edited participant has a first and lastname that already exist in event.
     * @param firstname the new firstname of person.
     * @param lastname the new lastname of person.
     * @return true if another person with same names exist.
     */
    public boolean personExists(String firstname, String lastname) {
        List<Person> allPersons = server.getPeopleInCurrentEvent(mainCtrl.getCurrentEventID());
        boolean personIsDuplicate = false;
        for (Person e : allPersons) {
            if (firstname.equals(e.getFirstName()) && lastname.equals(e.getLastName())) {
                warningLabel.setText("A person with this combination of first and last name already exists. " +
                        "Please rename this, or the other equally named person.");
                System.out.println("tried to add a combination of firstname and lastname that already exists");
                if (!(participantsScroll.getSelectionModel().getSelectedItem() == e)) {
                    personIsDuplicate = true;
                }
            }
        }
        return personIsDuplicate;
    }


    public void removePerson(){
        Person person = participantsScroll.getSelectionModel().getSelectedItem();
        int personID = person.getId();
        int serverID = mainCtrl.getCurrentEventID();
        Person removedPerson = server.removePerson(personID, serverID);
        System.out.println(removedPerson);
    }

    /**
     * Method for the abort button.
     */
    public void abort () {
        clearFields();
        mainCtrl.showEventPage(mainCtrl.getCurrentEventID());
    }

    /**
     * Clears all the input fields.
     */
    private void clearFields () {
        email.clear();
        firstName.clear();
        lastName.clear();
        iban.clear();
        originalIBAN.setText("");
        originalEmail.setText("");
        originalFName.setText("");
        originalLName.setText("");
    }


    }


