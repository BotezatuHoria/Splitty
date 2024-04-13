/**
 * Sample Skeleton for 'AddExpense.fxml' Controller Class
 */

package client.scenes;

import client.Config;
import client.utils.LanguageSingleton;
import client.utils.ServerUtils;
import commons.Person;
import commons.Tag;
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

    @FXML // fx:id="remove"
    private Button remove; // Value injected by FXMLLoader

    @FXML // fx:id="saveButton"
    private Button saveButton; // Value injected by FXMLLoader

    @FXML // fx:id="addButton"
    private Button addButton; // Value injected by FXMLLoader

    @FXML // fx:id="addEverybody"
    private Button addEverybody; // Value injected by FXMLLoader

    @FXML // fx:id="currencyBox"
    private ComboBox<String> currencyBox; // Value injected by FXMLLoader

    @FXML // fx:id="expenseScroll"
    private ComboBox<Integer> expenseScroll; // Value injected by FXMLLoader

    @FXML // fx:id="dateBox"
    private DatePicker dateBox; // Value injected by FXMLLoader

    @FXML // fx:id="addExpenseTitle"
    private Label addExpenseTitle; // Value injected by FXMLLoader

    @FXML // fx:id="whoPaidLabel"
    private Label whoPaidLabel; // Value injected by FXMLLoader

    @FXML // fx:id="whatForLabel"
    private Label whatForLabel; // Value injected by FXMLLoader

    @FXML // fx:id="howMuchLabel"
    private Label howMuchLabel; // Value injected by FXMLLoader

    @FXML // fx:id="whenLabel"
    private Label whenLabel; // Value injected by FXMLLoader

    @FXML // fx:id="howToLabel"
    private Label howToLabel; // Value injected by FXMLLoader

    @FXML // fx:id="expenseTypeLabel"
    private Label expenseTypeLabel; // Value injected by FXMLLoader

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
        currencyBox.getItems().add("EUR");
        Config config = ServerUtils.getConfig();
        String foodString = LanguageSingleton.getInstance().getResourceBundle().getString("food.label");
        String entranceFeeString= LanguageSingleton.getInstance().getResourceBundle().getString("entrance.fee.label");
        String travelString = LanguageSingleton.getInstance().getResourceBundle().getString("travel.label");
        for (Tag t : server.getEventByID(mainCtrl.getCurrentEventID()).getTagList()) {
            if (!config.getClientsLanguage().equals("en")) {
                if (t.getTitle().equals("Food")) {
                    t.setTitle(foodString);
                }
                if (t.getTitle().equals("Entrance Fees")) {
                    t.setTitle(entranceFeeString);
                }
                if (t.getTitle().equals("Travel")) {
                    t.setTitle(travelString);
                }
            }
            expenseTypeBox.getItems().add(t.getTitle());
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
        server.addTag(new Tag(newTagField.getText().trim()), mainCtrl.getCurrentEventID());
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
            int currency = 840;
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
            String expenseCreatedAlert = LanguageSingleton.getInstance().getResourceBundle().getString("expense.created.alert");

            alert.setContentText(expenseCreatedAlert);
            alert.showAndWait();
        }catch (Exception e) {
            String expenseFailedAlert = LanguageSingleton.getInstance().getResourceBundle().getString("expense.created.fail.alert");

            mainCtrl.showAlert(expenseFailedAlert);
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
        LanguageSingleton lang = LanguageSingleton.getInstance();
        ResourceBundle messages = lang.getResourceBundle();

        if (expenseField.getText() == null || expenseField.getText().equals(" ")) {
            mainCtrl.showAlert(messages.getString("expense.validation.error.provideExpense"));
            return false;
        }
        if (priceField.getText() == null || priceField.getText().equals(" ")) {
            mainCtrl.showAlert(messages.getString("expense.validation.error.provideAmount"));
            return false;
        }
        try {
            double x = Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            mainCtrl.showAlert(messages.getString("expense.validation.error.validAmount"));
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
        LanguageSingleton lang = LanguageSingleton.getInstance();
        ResourceBundle messages = lang.getResourceBundle();

        if (payerBox.valueProperty().get() == null) {
            mainCtrl.showAlert(messages.getString("validation.error.providePayer"));
            return false;
        }

        if (currencyBox.valueProperty().get() == null) {
            mainCtrl.showAlert(messages.getString("validation.error.provideCurrency"));
            return false;
        }

        if (dateBox.valueProperty().get() == null) {
            mainCtrl.showAlert(messages.getString("validation.error.provideDate"));
            return false;
        }

        if (expenseTypeBox.valueProperty().get() == null) {
            mainCtrl.showAlert(messages.getString("validation.error.provideExpenseType"));
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
        mainCtrl.showAlert(LanguageSingleton.getInstance().getResourceBundle().getString("selection.error.minPerson"));
        return false;
    }

    public void setLanguageText(ResourceBundle resourceBundle) {
        addExpenseTitle.setText(resourceBundle.getString("title.text"));
        whoPaidLabel.setText(resourceBundle.getString("who.text"));
        whatForLabel.setText(resourceBundle.getString("what.text"));
        howMuchLabel.setText(resourceBundle.getString("much.text"));
        whenLabel.setText(resourceBundle.getString("when.text"));
        howToLabel.setText(resourceBundle.getString("split.text"));
        expenseTypeLabel.setText(resourceBundle.getString("type.text"));
        addEverybody.setText(resourceBundle.getString("split.button"));
        abortButton.setText(resourceBundle.getString("abort.button"));
        addEverybody.setText(resourceBundle.getString("split.button"));
        payerBox.setPromptText(resourceBundle.getString("payer.menu"));
        expenseField.setPromptText(resourceBundle.getString("what.textfield"));
        priceField.setPromptText(resourceBundle.getString("much.textfield"));
        dateBox.setPromptText(resourceBundle.getString("when.textfield"));
        currencyBox.setPromptText(resourceBundle.getString("currency.menu"));
        expenseTypeBox.setPromptText(resourceBundle.getString("type.menu"));
        newTagField.setPromptText(resourceBundle.getString("newType.textfield"));
        addButton.setText(resourceBundle.getString("add.button"));
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