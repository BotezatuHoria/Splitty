/**
 * Sample Skeleton for 'AddExpense.fxml' Controller Class
 */

package client.scenes;

import commons.Event;
import commons.Person;
import commons.Transaction;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class AddExpenseCtrl {

    @FXML // fx:id="expensePane"
    private AnchorPane expensePane; // Value injected by FXMLLoader

    @FXML // fx:id="abortButton"
    private Button abortButton; // Value injected by FXMLLoader

    @FXML // fx:id="addButton"
    private Button addButton; // Value injected by FXMLLoader

    @FXML // fx:id="addEverybody"
    private Button addEverybody; // Value injected by FXMLLoader

    @FXML // fx:id="currencyBox"
    private ComboBox<?> currencyBox; // Value injected by FXMLLoader

    @FXML // fx:id="dateBox"
    private DatePicker dateBox; // Value injected by FXMLLoader

    @FXML // fx:id="errorLabel"
    private Label errorLabel; // Value injected by FXMLLoader

    @FXML // fx:id="expenseField"
    private TextField expenseField; // Value injected by FXMLLoader

    @FXML // fx:id="expenseTypeBox"
    private ComboBox<Transaction> expenseTypeBox; // Value injected by FXMLLoader

    @FXML // fx:id="payerBox"
    private ComboBox<Person> payerBox; // Value injected by FXMLLoader

    @FXML // fx:id="peopleLIstView"
    private ListView<CheckBox> peopleLIstView; // Value injected by FXMLLoader

    @FXML // fx:id="priceField"
    private TextField priceField; // Value injected by FXMLLoader

    //private final ServerUtils server;

    private final MainCtrl mainCtrl;

    /**
     * Constructor for the add expense controller.
     * @param mainCtrl - reference to the main controller
     */
    @Inject
    public AddExpenseCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
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

    /**
     * Method that adds participants to the listView.
     * Full functionality will be implemented in the future.
     */
    public void addParticipantToView() {
        Event e = new Event("", "", 1, ""
                , new HashSet<>(), new HashSet<>());
        Person p =
                new Person("idk", "Horia", "Botezatu", "2334", e,
                        new HashSet<>(), new HashSet<>());
        CheckBox personCheck = new CheckBox(p.getFirstName() + " " + p.getLastName());
        peopleLIstView.getItems().add(personCheck);
        addAllParticipants();
    }

    /**
     * Method that prints all nodes.
     */
    public void showNodes() {
        List<Node> nodes = getNodes();
        for (Node node : nodes) {
            System.out.println(node);
        }
    }

    /**
     * Method that checks all the checkBoxes for all the participants in the list view.
     */
    public void addAllParticipants() {
        for (CheckBox checkBox : peopleLIstView.getItems()) {
            checkBox.setSelected(true);
        }
    }

    /**
     * Method for clearing all the inputs after adding a new expense.
     */
    public void clearInputs() {
        payerBox.valueProperty().set(null);
        expenseField.clear();
        priceField.clear();
        currencyBox.valueProperty().set(null);
        dateBox.valueProperty().set(null);
        expenseTypeBox.valueProperty().set(null);
        peopleLIstView.refresh();
    }

    /**
     * Function for the add button.
     */
    public void addExpense() {
        showNodes();
        if (checkCompleted()) {
            clearInputs();
            //send data to server databae
        }
    }

    /**
     * Function that returns every Node in the primary pane of the page.
     *
     * @return - a list of all elements in the expensePane
     */
    public List<Node> getNodes() {
        List<Node> nodes = new LinkedList<>();
        nodes.addAll(expensePane.getChildren());
        return nodes;
    }

    /**
     * Function for the abort button of the expense page.
     */
    public void abortExpense() {
        clearInputs();
        mainCtrl.showEventPage();
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
            errorLabel.setText("Please provide the expense!");
            return false;
        }
        if ((priceField.getText() == null)) {
            errorLabel.setText("Please provide the amount of the expense!");
            return false;
        }

        try {
            double x = Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            errorLabel.setText("Please provide a valid amount.");
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
            errorLabel.setText("Please provide who paid! ");
            return false;
        }

        if (currencyBox.valueProperty().get() == null) {
            errorLabel.setText("Please select a currency!");
            return false;
        }

        if (dateBox.valueProperty().get() == null) {
            errorLabel.setText("Please select a valid date!");
            return false;
        }

        if (expenseTypeBox.valueProperty().get() == null) {
            errorLabel.setText("Please select a valid type of expense!");
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
        errorLabel.setText("Please select at least a person!");
        return false;
    }

}
