/**
 * Sample Skeleton for 'AddExpense.fxml' Controller Class
 */

package client.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AddExpenseCtrl {

    @FXML // fx:id="abortButton"
    private Button abortButton; // Value injected by FXMLLoader

    @FXML // fx:id="addButton"
    private Button addButton; // Value injected by FXMLLoader

    @FXML // fx:id="addButton1"
    private Button addButton1; // Value injected by FXMLLoader

    @FXML // fx:id="currencyBox"
    private ComboBox<?> currencyBox; // Value injected by FXMLLoader

    @FXML // fx:id="dateBox"
    private DatePicker dateBox; // Value injected by FXMLLoader

    @FXML // fx:id="expenseLabel"
    private TextField expenseLabel; // Value injected by FXMLLoader

    @FXML // fx:id="expenseTypeBox"
    private ComboBox<?> expenseTypeBox; // Value injected by FXMLLoader

    @FXML // fx:id="payerBox"
    private ComboBox<?> payerBox; // Value injected by FXMLLoader

    @FXML // fx:id="peopleLIstView"
    private ListView<?> peopleLIstView; // Value injected by FXMLLoader

    @FXML // fx:id="priceLabel"
    private TextField priceLabel; // Value injected by FXMLLoader

}
