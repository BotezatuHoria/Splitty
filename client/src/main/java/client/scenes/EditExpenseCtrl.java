package client.scenes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import client.utils.ServerUtils;
import commons.Person;
import commons.Transaction;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;


public class EditExpenseCtrl {

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

    @FXML
    public void abortExpense() {
        mainCtrl.showEventPage(mainCtrl.getCurrentEventID());
    }

    @FXML
    public void addNewTag() {
        expenseTypeBox.getItems().add(newTagField.getText());
        newTagField.clear();
        tagPane.visibleProperty().set(false);
    }

    @FXML
    public void addParticipantToView() {
        for (CheckBox checkBox : peopleLIstView.getItems()) {
            checkBox.setSelected(true);
        }
    }

    @FXML
    public void removeExpense() {
        if(expenseScroll.getValue() == null) {mainCtrl.showEventPage(mainCtrl.getCurrentEventID());}
        server.deleteTransactionFromCurrentEvent(mainCtrl.getCurrentEventID(), expenseScroll.getValue().getId());
        mainCtrl.showEventPage(mainCtrl.getCurrentEventID());
    }

    @FXML
    public void saveExpense() {


    }

    @FXML
    public void showTagPage() {
        tagPane.visibleProperty().set(true);
    }

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
                if(transaction.getParticipants().contains((Person) checkBox.getUserData())) {
                    checkBox.setSelected(true);
                }
            }
        }

    }

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


    @Inject
    public EditExpenseCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

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
