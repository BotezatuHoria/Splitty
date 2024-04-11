package client.scenes;
import java.net.URL;
import java.util.ResourceBundle;

import client.utils.ServerUtils;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
    private ComboBox<?> currencyBox; // Value injected by FXMLLoader

    @FXML // fx:id="dateBox"
    private DatePicker dateBox; // Value injected by FXMLLoader

    @FXML // fx:id="expensePane"
    private AnchorPane expensePane; // Value injected by FXMLLoader

    @FXML // fx:id="giveMoneyTitle"
    private Label giveMoneyTitle; // Value injected by FXMLLoader

    @FXML // fx:id="howMuchLabel"
    private Label howMuchLabel; // Value injected by FXMLLoader

    @FXML // fx:id="payeeBox"
    private ComboBox<?> payeeBox; // Value injected by FXMLLoader

    @FXML // fx:id="payerBox"
    private ComboBox<?> payerBox; // Value injected by FXMLLoader

    @FXML // fx:id="priceField"
    private TextField priceField; // Value injected by FXMLLoader

    @FXML // fx:id="toWhomLabel1"
    private Label toWhomLabel1; // Value injected by FXMLLoader

    @FXML // fx:id="whenLabel"
    private Label whenLabel; // Value injected by FXMLLoader

    @FXML // fx:id="whoPaidLabel"
    private Label whoPaidLabel; // Value injected by FXMLLoader


    @FXML
    void PayeeScroll(ActionEvent event) {

    }

    @FXML
    void abortExpense(ActionEvent event) {

    }

    @FXML
    void addExpense(ActionEvent event) {

    }

    @FXML
    void currencyScroll(ActionEvent event) {

    }

    @FXML
    void payerScroll(ActionEvent event) {

    }

    private MainCtrl mainCtrl;
    private ServerUtils server;

    @Inject
    public GiveMoneyCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void clear() {

    }

    public void updatePage() {

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

}
