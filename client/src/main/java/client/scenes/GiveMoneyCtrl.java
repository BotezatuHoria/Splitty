package client.scenes;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client.utils.LanguageSingleton;
import client.utils.ServerUtils;
import commons.Person;
import commons.Transaction;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

public class GiveMoneyCtrl implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="abortButton"
    private Button abortButton; // Value injected by FXMLLoader

    @FXML // fx:id="addButton"
    private Button addButton; // Value injected by FXMLLoader

    @FXML // fx:id="currencyBox"
    private ComboBox<String> currencyBox; // Value injected by FXMLLoader

    @FXML // fx:id="dateBox"
    private DatePicker dateBox; // Value injected by FXMLLoader

    @FXML // fx:id="expensePane"
    private AnchorPane expensePane; // Value injected by FXMLLoader

    @FXML // fx:id="giveMoneyTitle"
    private Label giveMoneyTitle; // Value injected by FXMLLoader

    @FXML // fx:id="howMuchLabel"
    private Label howMuchLabel; // Value injected by FXMLLoader

    @FXML // fx:id="payeeBox"
    private ComboBox<Person> payeeBox; // Value injected by FXMLLoader

    @FXML // fx:id="payerBox"
    private ComboBox<Person> payerBox; // Value injected by FXMLLoader

    @FXML // fx:id="priceField"
    private TextField priceField; // Value injected by FXMLLoader

    @FXML // fx:id="toWhomLabel1"
    private Label toWhomLabel1; // Value injected by FXMLLoader

    @FXML // fx:id="whenLabel"
    private Label whenLabel; // Value injected by FXMLLoader

    @FXML // fx:id="whoPaidLabel"
    private Label whoPaidLabel; // Value injected by FXMLLoader
    private MainCtrl mainCtrl;
    private ServerUtils server;

    /**
     * Aborts the expense and goes back to the event page.
     */
    @FXML
    void abortExpense() {
        mainCtrl.showEventPage(mainCtrl.getCurrentEventID());
    }

    /**
     * Adds expense to the event.
     */
    @FXML
    void addExpense() {
        if (checkCompleted()) {
            addTransaction();
            clear();
            mainCtrl.showEventPage(mainCtrl.getCurrentEventID());
        }

    }

    /**
     * Gets all the fields that had to be entered.
     */
    public void addTransaction() {
        try {
            Person payer = payerBox.getValue();
            Person payee = payeeBox.getValue();
            double value = Double.parseDouble(priceField.getText());
            LocalDate date = dateBox.getValue();
            int currency = 840;
            List<Person> participants = new ArrayList<>();
            participants.add(payee);
            String title = payer +  " " +
                    LanguageSingleton.getInstance().getResourceBundle().getString("pays.misc") + " " + payee;
            String expenseType = null;
            Transaction transaction = new Transaction(title, date, value, currency, expenseType, participants, payer);
            transaction.setHandOff(true);
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
     * Constructor.
     * @param mainCtrl the main ctrl
     * @param server to get stuff
     */
    @Inject
    public GiveMoneyCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Clears the page.
     */
    public void clear() {
        payerBox.getItems().clear();
        payeeBox.getItems().clear();
        priceField.clear();
        currencyBox.getItems().clear();
        dateBox.valueProperty().set(null);
    }

    /**
     * Updates the page by loading all required stuff.
     */
    public void updatePage() {
        clear();
        List<Person> people = server.getPeopleInCurrentEvent(mainCtrl.getCurrentEventID());
        addPeopleToPayerAndPayeeBox(people);
        currencyBox.getItems().add("EUR");
    }

    /**
     * Method that adds all the people in the peoplePlayerBox.
     * @param people - people to be added
     */
    public void addPeopleToPayerAndPayeeBox(List<Person> people) {
        payerBox.getItems().clear();
        payeeBox.getItems().clear();
        for (Person p : people) {
            payerBox.getItems().add(p);
            payeeBox.getItems().add(p);
        }
    }


    /**
     * Fxml method to initialize stuff.
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert abortButton != null : "fx:id=\"abortButton\" was not injected: check your FXML file 'GiveMoney.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'GiveMoney.fxml'.";
        assert currencyBox != null : "fx:id=\"currencyBox\" was not injected: check your FXML file 'GiveMoney.fxml'.";
        assert dateBox != null : "fx:id=\"dateBox\" was not injected: check your FXML file 'GiveMoney.fxml'.";
        assert expensePane != null : "fx:id=\"expensePane\" was not injected: check your FXML file 'GiveMoney.fxml'.";
        assert giveMoneyTitle != null : "fx:id=\"giveMoneyTitle\" was not injected: check your FXML file 'GiveMoney.fxml'.";
        assert howMuchLabel != null : "fx:id=\"howMuchLabel\" was not injected: check your FXML file 'GiveMoney.fxml'.";
        assert payeeBox != null : "fx:id=\"payeeBox\" was not injected: check your FXML file 'GiveMoney.fxml'.";
        assert payerBox != null : "fx:id=\"payerBox\" was not injected: check your FXML file 'GiveMoney.fxml'.";
        assert priceField != null : "fx:id=\"priceField\" was not injected: check your FXML file 'GiveMoney.fxml'.";
        assert toWhomLabel1 != null : "fx:id=\"toWhomLabel1\" was not injected: check your FXML file 'GiveMoney.fxml'.";
        assert whenLabel != null : "fx:id=\"whenLabel\" was not injected: check your FXML file 'GiveMoney.fxml'.";
        assert whoPaidLabel != null : "fx:id=\"whoPaidLabel\" was not injected: check your FXML file 'GiveMoney.fxml'.";

    }

    /**
     * Method that checks that all fields and boxes are filled.
     * @return true if everything is filled.
     */
    public boolean checkCompleted() {return checkBoxes() && checkFields();}

    /**
     * checks that all input is correct per box.
     * @return true if boxes are filled correctly, false otherwise.
     */
    private boolean checkBoxes() {
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

        if (payerBox.getValue().equals(payeeBox.getValue())) {
            mainCtrl.showAlert(messages.getString("validation.error.differentPayee"));
            return false;
        }

        return true;
    }

    /**
     * Checks that all fields are full.
     * @return true if they are indeed full
     */
    private boolean checkFields() {
        LanguageSingleton lang = LanguageSingleton.getInstance();
        ResourceBundle messages = lang.getResourceBundle();

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
     * Sets the language for the page.
     * @param resourceBundle that holds all keys and words.
     */
    public void setLanguageText(ResourceBundle resourceBundle) {
        //fromParticipant.setText(resourceBundle.getString("from.button"));
        giveMoneyTitle.setText(resourceBundle.getString("giveMoney.label"));
        whoPaidLabel.setText(resourceBundle.getString("who.Paid"));
        toWhomLabel1.setText(resourceBundle.getString("to.whom"));
        howMuchLabel.setText(resourceBundle.getString("how.Much"));
        whenLabel.setText(resourceBundle.getString("when.label"));
        payerBox.setPromptText(resourceBundle.getString("payer.box"));
        payeeBox.setPromptText(resourceBundle.getString("payee.box"));
        currencyBox.setPromptText(resourceBundle.getString("currency.menu"));
        priceField.setPromptText(resourceBundle.getString("much.textfield"));
        dateBox.setPromptText(resourceBundle.getString("when.textfield"));
        addButton.setText(resourceBundle.getString("add.button"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateBox.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                dateBox.show();
            }
        });
    }
}
