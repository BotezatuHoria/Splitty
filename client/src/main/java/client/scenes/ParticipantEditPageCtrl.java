package client.scenes;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import client.utils.LanguageManager;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Person;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Modality;

public class ParticipantEditPageCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final LanguageManager languageManager;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button abortButton;

    @FXML
    private Button removeParticipantButton;

    @FXML
    private Label editParticipantLabel;

    @FXML
    private Label selectParticipantLabel;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label originalLabel;

    @FXML
    private Label newValuesLabel;

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
    public ParticipantEditPageCtrl(ServerUtils server, MainCtrl mainCtrl, LanguageManager languageManager) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.languageManager = languageManager;
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
    }

    /**
     * is the selector of the dropdown menu of participants of this event.
     */
    public void selectParticipant() {
        Person person = participantsScroll.getSelectionModel().getSelectedItem();
        System.out.println("Person selected to edit: " + person);
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
            warningLabel.setText(languageManager.getResourceBundle().getString("warning.fields.empty"));
        }
        else if (!(personExists(newFirstName, newLastName))) {
            person.setFirstName(newFirstName);
            person.setLastName(newLastName);
            person.setEmail(newEmail);
            person.setIban(newIban);
            Person updatedperson = server.updatePerson(person.getId(), person);
            System.out.println("PERSON EDITED: person with id " + person.getId() + " was adjusted to " + updatedperson);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(languageManager.getResourceBundle().getString("person.edit.success"));
            alert.showAndWait();
            clearFields();
            mainCtrl.showEventPage(mainCtrl.getCurrentEventID());
        } else {
            if(!(personExists(newFirstName,newLastName))){
                warningLabel.setText(languageManager.getResourceBundle().getString("error.generic"));
            }
        }


    }

    /**
     * checks whether the edited participant has a first and lastname that already exist in event.
     * @param firstname the new firstname of person.
     * @param lastname the new lastname of person.
     * @return true if another person with same names exist.
     */
    public boolean personExists(String firstname, String lastname) {
        List<Person> allPersons = server.getPeopleInCurrentEvent(mainCtrl.getCurrentEventID());
        boolean personIsDuplicate = false;
        for (Person e : allPersons) {
            if (firstname.equals(e.getFirstName()) && lastname.equals(e.getLastName())) {
                if (!(participantsScroll.getSelectionModel().getSelectedItem().equals(e))) {
                    personIsDuplicate = true;
                    warningLabel.setText(languageManager.getResourceBundle().getString("error.duplicate.name"));
                    System.out.println("ADDING FAILED: Tried to add a combination of firstname and lastname that already exists");
                    return personIsDuplicate;
                }

            }
        }
        return personIsDuplicate;
    }


    /**
     *removes a person from the event.
     */
    public void removePerson(){
        Person person = participantsScroll.getSelectionModel().getSelectedItem();
        int personID = person.getId();
        int serverID = mainCtrl.getCurrentEventID();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setContentText(languageManager.getResourceBundle().getString("confirm.delete.person"));
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            Person removedPerson = server.removePerson(personID, serverID);
            System.out.println(removedPerson);
            abort();
        }
        else{
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setContentText(languageManager.getResourceBundle().getString("person.delete.cancelled"));
            alert2.show();
        }
    }

    /**
     * Method for the abort button.
     */
    public void abort () {
        clearFields();
        mainCtrl.showEventPage(mainCtrl.getCurrentEventID());
    }

    /**
     * sets all the text in the file to the correct language chosen by the user.
     * @param resourceBundle the language.
     */
    public void setLanguageText(ResourceBundle resourceBundle) {
        editParticipantLabel.setText(resourceBundle.getString("edit.participant.title"));
        selectParticipantLabel.setText(resourceBundle.getString("edit.participant.selectLabel"));
        firstNameLabel.setText(resourceBundle.getString("edit.participant.firstName"));
        lastNameLabel.setText(resourceBundle.getString("edit.participant.lastName"));
        originalLabel.setText(resourceBundle.getString("edit.participant.column.original"));
        newValuesLabel.setText(resourceBundle.getString("edit.participant.column.newValue"));
        firstName.setPromptText(resourceBundle.getString("edit.participant.firstName"));
        lastName.setPromptText(resourceBundle.getString("edit.participant.lastName"));
        participantsScroll.setPromptText(resourceBundle.getString("edit.participant.comboBoxPrompt"));
        abortButton.setText(resourceBundle.getString("edit.participant.button.abort"));
        removeParticipantButton.setText(resourceBundle.getString("edit.participant.button.remove"));
        saveButton.setText(resourceBundle.getString("edit.participant.button.save"));

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        server.registerForMessages("/topic/events/people", Person.class, person -> {
            Platform.runLater(this::updatePage);
        });
    }
}


