/**
 * Sample Skeleton for 'AddExpense.fxml' Controller Class
 */

package client.scenes;

import commons.Event;
import commons.PersonTemporary;
import commons.Transaction;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.inject.Inject;
import java.util.HashSet;

public class AddExpenseCtrl {

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

    @FXML // fx:id="expenseLabel"
    private TextField expenseLabel; // Value injected by FXMLLoader

    @FXML // fx:id="expenseTypeBox"
    private ComboBox<Transaction> expenseTypeBox; // Value injected by FXMLLoader

    @FXML // fx:id="payerBox"
    private ComboBox<PersonTemporary> payerBox; // Value injected by FXMLLoader

    @FXML // fx:id="peopleLIstView"
    private ListView<CheckBox> peopleLIstView; // Value injected by FXMLLoader

    @FXML // fx:id="priceLabel"
    private TextField priceLabel; // Value injected by FXMLLoader

    //private final ServerUtils server;

    private final MainCtrl mainCtrl;
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
        assert expenseLabel != null : "fx:id=\"expenseLabel\" was not injected: check your FXML file 'AddExpense.fxml'.";
        assert expenseTypeBox != null : "fx:id=\"expenseTypeBox\" was not injected: check your FXML file 'AddExpense.fxml'.";
        assert payerBox != null : "fx:id=\"payerBox\" was not injected: check your FXML file 'AddExpense.fxml'.";
        assert peopleLIstView != null : "fx:id=\"peopleLIstView\" was not injected: check your FXML file 'AddExpense.fxml'.";
        assert priceLabel != null : "fx:id=\"priceLabel\" was not injected: check your FXML file 'AddExpense.fxml'.";

    }

    public void addParticipantToView() {
        Event e = new Event("", "", 1, ""
                , new HashSet<>(), new HashSet<>());
        PersonTemporary p =
                new PersonTemporary("idk", "Horia", "Botezatu", "2334", e,
                        new HashSet<>(), new HashSet<>());
        CheckBox personCheck = new CheckBox(p.getFirstName() + " " + p.getLastName());
        peopleLIstView.getItems().add(personCheck);
    }

}
