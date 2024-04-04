/**
 * Sample Skeleton for 'AddExpense.fxml' Controller Class
 */

package client.scenes;

import client.utils.ServerUtils;
import commons.Person;
import commons.Transaction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import com.google.inject.Inject;
import javafx.stage.Modality;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class AddExpenseCtrl implements Initializable {

    @FXML // fx:id="expensePane"
    private AnchorPane expensePane; // Value injected by FXMLLoader

    @FXML // fx:id="abortButton"
    private Button abortButton; // Value injected by FXMLLoader

    @FXML // fx:id="addButton"
    private Button addButton; // Value injected by FXMLLoader

    @FXML // fx:id="addEverybody"
    private Button addEverybody; // Value injected by FXMLLoader

    @FXML // fx:id="currencyBox"
    private ComboBox<Integer> currencyBox; // Value injected by FXMLLoader

    @FXML // fx:id="dateBox"
    private DatePicker dateBox; // Value injected by FXMLLoader

    @FXML // fx:id="expenseField"
    private TextField expenseField; // Value injected by FXMLLoader

    @FXML // fx:id="expenseTypeBox"
    private ComboBox<String> expenseTypeBox; // Value injected by FXMLLoader

    @FXML // fx:id="payerBox"
    private ComboBox<Person> payerBox; // Value injected by FXMLLoader

    @FXML // fx:id="peopleLIstView"
    private ListView<CheckBox> peopleLIstView; // Value injected by FXMLLoader

    @FXML // fx:id="priceField"
    private TextField priceField; // Value injected by FXMLLoader

    @FXML
    private AnchorPane tagPane;

    @FXML
    private TextField newTagField;

    @FXML
    private Button addTagButton;

    //private final ServerUtils server;

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     * Constructor for the add expense controller.
     * @param mainCtrl - reference to the main controller
     */
    @Inject
    public AddExpenseCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Initialize method for the page.
     * @param url -
     * @param resourceBundle -
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tagPane.visibleProperty().set(false);
    }

    /**
     * Method that checks all the checkBoxes for all the participants in the list view.
     */
    public void addParticipantToView() {
        for (CheckBox checkBox : peopleLIstView.getItems()) {
            checkBox.setSelected(true);
        }
    }

    /**
     * Method for clearing all the inputs after adding a new expense.
     */
    public void clearInputs() {
        payerBox.getItems().clear();
        expenseField.clear();
        priceField.clear();
        currencyBox.getItems().clear();
        dateBox.valueProperty().set(null);
        expenseTypeBox.getItems().clear();
        peopleLIstView.getItems().clear();
    }

    /**
     * Function for the add button.
     */
    public void addExpense() {
        if (checkCompleted()) {
            createTransaction();
            clearInputs();
            mainCtrl.showEventPage(mainCtrl.getCurrentEventID());
        }
    }

    /**
     * Method that retrieves all the people from an event from the database.
     */
    public void retrievePeopleFromDb() {
        List<Person> people = server.getPeopleInCurrentEvent(mainCtrl.getCurrentEventID());
        addPeopleToView(people);
        addPeopleToPayerBox(people);
        currencyBox.getItems().add(840);
        expenseTypeBox.getItems().add("Food");
        expenseTypeBox.getItems().add("Entrance fees");
        expenseTypeBox.getItems().add("Travel");
    }

    /**
     * Method that adds all the people in the personView.
     * @param people - people to be added
     */
    public void addPeopleToView(List<Person> people) {
        for (Person p : people) {
            CheckBox checkBox = new CheckBox(p.toString());
            checkBox.setUserData(p);
            peopleLIstView.getItems().add(checkBox);
        }
    }

    /**
     * Method that adds all the people in the peoplePlayerBox.
     * @param people - people to be added
     */
    public void addPeopleToPayerBox(List<Person> people) {
        payerBox.getItems().clear();
        for (Person p : people) {
            payerBox.getItems().add(p);
        }
    }

    /**
     * Method that will be implemented for the currencies.
     * @param currencies - set of all currencies
     */
    public void addCurrencies(List<Currency> currencies) {
        // to be implemented with all the currencies that will be available in the project;
    }

    /**
     * shows the tags, set to a true visibility.
     */
    public void showTagPage() {
        tagPane.visibleProperty().set(true);
    }

    /**
     * adds new tag to the expense.
     */
    public void addNewTag() {
        expenseTypeBox.getItems().add(newTagField.getText());
        newTagField.clear();
        tagPane.visibleProperty().set(false);
    }

    /**
     * Method that creates a new Transaction and adds it to the database.
     */
    public void createTransaction() {
        try {
            Person payer = payerBox.getValue();
            String title = expenseField.getText();
            double value = Double.parseDouble(priceField.getText());
            LocalDate date = dateBox.getValue();
            int currency = currencyBox.getValue();
            List<Person> participants = new ArrayList<>();
            for (CheckBox checkBox : peopleLIstView.getItems()) {
                if (checkBox.isSelected()) {
                    participants.add((Person) checkBox.getUserData());
                }
            }
            String expenseType = expenseTypeBox.getValue();
            Transaction transaction = new Transaction(title, date, value, currency, expenseType, participants, payer);
            System.out.println(transaction.getCreator().toString());
            Transaction result = server.addTransactionToCurrentEvent(mainCtrl.getCurrentEventID(), transaction);
            System.out.println(result.toString());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("Expense created successfully!");
            alert.showAndWait();
        }catch (Exception e) {
            mainCtrl.showAlert("Transaction was not created");
        }

    }

    /**
     * Function for the abort button of the expense page.
     */
    public void abortExpense() {
        clearInputs();
        mainCtrl.showEventPage(mainCtrl.getCurrentEventID());
    }

    /**
     * Method that checks if every input in the scene has been modified.
     *
     * @return - true if all inputs have a value, otherwise false.
     */
    public boolean checkCompleted() {
        return checkBoxes() && checkFields() && checkListView();
    }

    /**
     * Method that checks if all the input fields have been completed.
     *
     * @return - true if all fields have a value, otherwise false.
     */
    public boolean checkFields() {
        if (expenseField.getText() == null || expenseField.getText().equals(" ")) {
            mainCtrl.showAlert("Please provide the expense!");
            return false;
        }
        if ((priceField.getText() == null)) {
            mainCtrl.showAlert("Please provide the amount of the expense!");
            return false;
        }
        try {
            double x = Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            mainCtrl.showAlert("Please provide a valid amount.");
            return false;
        }

        return true;
    }

    /**
     * Method that checks if all the combo boxes have a selected element.
     *
     * @return - true if anything is selected, otherwise false.
     */
    public boolean checkBoxes() {
        if (payerBox.valueProperty().get() == null) {
            mainCtrl.showAlert("Please provide who paid! ");
            return false;
        }

        if (currencyBox.valueProperty().get() == null) {
            mainCtrl.showAlert("Please select a currency!");
            return false;
        }

        if (dateBox.valueProperty().get() == null) {
            mainCtrl.showAlert("Please select a valid date!");
            return false;
        }

        if (expenseTypeBox.valueProperty().get() == null) {
            mainCtrl.showAlert("Please select a valid type of expense!");
            return false;
        }
        return true;
    }

    /**
     * Method that checks if there is at least one person selected.
     *
     * @return - true if there is at least one person selected, otherwise false.
     */
    public boolean checkListView() {
        boolean checked = false;
        for (CheckBox checkBox : peopleLIstView.getItems()) {
            if (checkBox.isSelected()) {
                checked = true;
                break;
            }
        }
        if (checked) {
            return true;
        }
        mainCtrl.showAlert("Please select at least a person!");
        return false;
    }

    public void setLanguageText(ResourceBundle resourceBundle) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert abortButton != null : "fx:id=\"abortButton\" was not injected: check your FXML file 'AddExpense.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'AddExpense.fxml'.";
        assert addEverybody != null : "fx:id=\"addEverybody\" was not injected: check your FXML file 'AddExpense.fxml'.";
        assert currencyBox != null : "fx:id=\"currencyBox\" was not injected: check your FXML file 'AddExpense.fxml'.";
        assert dateBox != null : "fx:id=\"dateBox\" was not injected: check your FXML file 'AddExpense.fxml'.";
        assert expensePane != null : "fx:id=\"expensePane\" was not injected: check your FXML file 'AddExpense.fxml'.";
        assert expenseTypeBox != null : "fx:id=\"expenseTypeBox\" was not injected: check your FXML file 'AddExpense.fxml'.";
        assert payerBox != null : "fx:id=\"payerBox\" was not injected: check your FXML file 'AddExpense.fxml'.";
        assert peopleLIstView != null : "fx:id=\"peopleLIstView\" was not injected: check your FXML file 'AddExpense.fxml'.";
        assert priceField != null : "fx:id=\"priceField\" was not injected: check your FXML file 'AddExpense.fxml'.";
        assert expenseField != null : "fx:id=\"expenseField\" was not injected: check your FXML file 'AddExpense.fxml'.";
    }
}