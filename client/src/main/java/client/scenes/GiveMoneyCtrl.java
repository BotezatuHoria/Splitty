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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

public class GiveMoneyCtrl {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="abortButton"
    private Button abortButton; // Value injected by FXMLLoader

    @FXML // fx:id="addButton"
    private Button addButton; // Value injected by FXMLLoader

    @FXML // fx:id="currencyBox"
    private ComboBox<Integer> currencyBox; // Value injected by FXMLLoader

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


    @FXML
    void abortExpense(ActionEvent event) {
        mainCtrl.showEventPage(mainCtrl.getCurrentEventID());
    }

    @FXML
    void addExpense(ActionEvent event) {
        if (checkCompleted()) {
            addTransaction();
            clear();
            mainCtrl.showEventPage(mainCtrl.getCurrentEventID());
        }

    }

    public void addTransaction() {
        try {
            Person payer = payerBox.getValue();
            Person payee = payeeBox.getValue();
            double value = Double.parseDouble(priceField.getText());
            LocalDate date = dateBox.getValue();
            int currency = currencyBox.getValue();
            List<Person> participants = new ArrayList<>();
            participants.add(payee);
            String title = payer + " pays " + payee;
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




    @Inject
    public GiveMoneyCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void clear() {
        payerBox.getItems().clear();
        payeeBox.getItems().clear();
        priceField.clear();
        currencyBox.getItems().clear();
        dateBox.valueProperty().set(null);
    }

    public void updatePage() {
        clear();
        List<Person> people = server.getPeopleInCurrentEvent(mainCtrl.getCurrentEventID());
        addPeopleToPayerAndPayeeBox(people);
        currencyBox.getItems().add(840);
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
     * @return
     */
    public boolean checkCompleted() {return checkBoxes() && checkFields();}

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
            mainCtrl.showAlert("Please choose a different payee than the payer");
            return false;
        }

        return true;
    }

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

}
