package client.scenes;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import client.utils.ServerUtils;
import commons.Person;
import commons.Transaction;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;


public class EditExpenseCtrl implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="abortButton"
    private Button abortButton; // Value injected by FXMLLoader

    @FXML // fx:id="addEverybody"
    private Button addEverybody; // Value injected by FXMLLoader

    @FXML // fx:id="addTag"
    private Button addTag; // Value injected by FXMLLoader

    @FXML // fx:id="addTagButton"
    private Button addTagButton; // Value injected by FXMLLoader

    @FXML // fx:id="currencyBox"
    private ComboBox<Integer> currencyBox; // Value injected by FXMLLoader

    @FXML // fx:id="dateBox"
    private DatePicker dateBox; // Value injected by FXMLLoader

    @FXML // fx:id="errorLabel"
    private Label errorLabel; // Value injected by FXMLLoader

    @FXML // fx:id="expenseField"
    private TextField expenseField; // Value injected by FXMLLoader

    @FXML // fx:id="expensePane"
    private AnchorPane expensePane; // Value injected by FXMLLoader

    @FXML // fx:id="expenseTypeBox"
    private ComboBox<String> expenseTypeBox; // Value injected by FXMLLoader

    @FXML // fx:id="newTagField"
    private TextField newTagField; // Value injected by FXMLLoader

    @FXML // fx:id="payerBox"
    private ComboBox<Person> payerBox; // Value injected by FXMLLoader

    @FXML // fx:id="peopleLIstView"
    private ListView<CheckBox> peopleLIstView; // Value injected by FXMLLoader

    @FXML // fx:id="priceField"
    private TextField priceField; // Value injected by FXMLLoader

    @FXML // fx:id="remove"
    private Button remove; // Value injected by FXMLLoader

    @FXML // fx:id="saveButton"
    private Button saveButton; // Value injected by FXMLLoader

    @FXML // fx:id="tagPane"
    private AnchorPane tagPane; // Value injected by FXMLLoader

    @FXML // fx:id="expenseScroll"
    private ComboBox<Transaction> expenseScroll;

    private MainCtrl mainCtrl;
    private ServerUtils server;

    /**
     * Button that aborts actions and returns to event page.
     */
    @FXML
    public void abortExpense() {
        mainCtrl.showEventPage(mainCtrl.getCurrentEventID());
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
     * Method that ads a new tag.
     */
    @FXML
    public void addNewTag() {
        expenseTypeBox.getItems().add(newTagField.getText());
        newTagField.clear();
        tagPane.visibleProperty().set(false);
    }

    /**
     * Method that adds participants to view.
     */
    @FXML
    public void addParticipantToView() {
        for (CheckBox checkBox : peopleLIstView.getItems()) {
            checkBox.setSelected(true);
        }
    }

    /**
     * Method that removes the selected Expense. It also prompts the user to make sure he wants to delete the expense.
     */
    @FXML
    public void removeExpense() {
        if(expenseScroll.getValue() == null) {
            mainCtrl.showAlert("No expense selected");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setContentText("Are you sure you want to delete this expense?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            server.deleteTransactionFromCurrentEvent(mainCtrl.getCurrentEventID(), expenseScroll.getValue().getId());
            mainCtrl.showEventPage(mainCtrl.getCurrentEventID());
        }
    }

    /**
     * Method that updates saves the updated transaction.
     */
    @FXML
    public void saveExpense() {
        if(expenseScroll.getValue() == null) {
            mainCtrl.showAlert("Please select a transaction to update.");
            return;
        }
        if (checkCompleted()) {
            updateTransaction();
            clear();
            mainCtrl.showEventPage(mainCtrl.getCurrentEventID());
        }
    }

    /**
     * Method that creates a new Transaction and adds it to the database.
     */
    public void updateTransaction() {
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
            Transaction result = server.updateTransactionByID(transaction, expenseScroll.getValue().getId());
            System.out.println(result.toString());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("Expense updated successfully!");
            alert.showAndWait();
        }catch (Exception e) {
            mainCtrl.showAlert("Transaction was not updated");
        }

    }

    /**
     * Method that checks that all fields and boxes are filled.
     * @return
     */
    public boolean checkCompleted() {
        return checkBoxes() && checkFields() && checkListView();
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

    /**
     * Method that shows the tag field.
     */
    @FXML
    public void showTagPage() {
        tagPane.visibleProperty().set(true);
    }

    /**
     * Method that puts all the data about a selected transaction in the fields and boxes.
     */
    @FXML
    public void expenseScroll() {
        Transaction transaction = expenseScroll.getValue();
        if(transaction != null) {
            payerBox.setValue(transaction.getCreator());
            expenseField.setText(transaction.getName());
            priceField.setText("" + transaction.getMoney());
            currencyBox.setValue(transaction.getCurrency());
            dateBox.setValue(transaction.getDate());
            if (!expenseTypeBox.getItems().contains(transaction.getExpenseType())) {
                expenseTypeBox.getItems().add(transaction.getExpenseType());
            }
            expenseTypeBox.setValue(transaction.getExpenseType());
            for(CheckBox checkBox: peopleLIstView.getItems()) {
                if (transaction.getParticipants().contains((Person) checkBox.getUserData())) {
                    checkBox.setSelected(true);
                }
                else {checkBox.setSelected(false);}
            }
        }

    }

    /**
     * Method that updates the page before loading it.
     */
    public void updatePage() {
        clear();
        loadTransactions();
        List<Person> people = server.getPeopleInCurrentEvent(mainCtrl.getCurrentEventID());
        addPeopleToPayerBox(people);
        addPeopleToView(people);
        currencyBox.getItems().add(840);
        expenseTypeBox.getItems().add("Food");
        expenseTypeBox.getItems().add("Entrance fees");
        expenseTypeBox.getItems().add("Travel");
    }

    /**
     * Method that loads all transaction in the expenseScroll.
     */
    public void loadTransactions() {
        expenseScroll.getItems().clear();
        List<Transaction> transactionList = server.getTransactions(mainCtrl.getCurrentEventID());
        for(Transaction transaction: transactionList) {
            System.out.println(transaction);
            expenseScroll.getItems().add(transaction);
        }
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
     * Constructor.
     * @param mainCtrl main controller
     * @param server server we are using
     */
    @Inject
    public EditExpenseCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Method that clears all inputs.
     */
    public void clear() {
        expenseScroll.getItems().clear();
        payerBox.getItems().clear();
        expenseField.clear();
        priceField.clear();
        currencyBox.getItems().clear();
        dateBox.valueProperty().set(null);
        expenseTypeBox.getItems().clear();
        peopleLIstView.getItems().clear();
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert abortButton != null : "fx:id=\"abortButton\" was not injected: check your FXML file 'EditExpense.fxml'.";
        assert addEverybody != null : "fx:id=\"addEverybody\" was not injected: check your FXML file 'EditExpense.fxml'.";
        assert addTag != null : "fx:id=\"addTag\" was not injected: check your FXML file 'EditExpense.fxml'.";
        assert addTagButton != null : "fx:id=\"addTagButton\" was not injected: check your FXML file 'EditExpense.fxml'.";
        assert currencyBox != null : "fx:id=\"currencyBox\" was not injected: check your FXML file 'EditExpense.fxml'.";
        assert dateBox != null : "fx:id=\"dateBox\" was not injected: check your FXML file 'EditExpense.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'EditExpense.fxml'.";
        assert expenseField != null : "fx:id=\"expenseField\" was not injected: check your FXML file 'EditExpense.fxml'.";
        assert expensePane != null : "fx:id=\"expensePane\" was not injected: check your FXML file 'EditExpense.fxml'.";
        assert expenseTypeBox != null : "fx:id=\"expenseTypeBox\" was not injected: check your FXML file 'EditExpense.fxml'.";
        assert newTagField != null : "fx:id=\"newTagField\" was not injected: check your FXML file 'EditExpense.fxml'.";
        assert payerBox != null : "fx:id=\"payerBox\" was not injected: check your FXML file 'EditExpense.fxml'.";
        assert peopleLIstView != null : "fx:id=\"peopleLIstView\" was not injected: check your FXML file 'EditExpense.fxml'.";
        assert priceField != null : "fx:id=\"priceField\" was not injected: check your FXML file 'EditExpense.fxml'.";
        assert remove != null : "fx:id=\"remove\" was not injected: check your FXML file 'EditExpense.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'EditExpense.fxml'.";
        assert tagPane != null : "fx:id=\"tagPane\" was not injected: check your FXML file 'EditExpense.fxml'.";

    }



}
