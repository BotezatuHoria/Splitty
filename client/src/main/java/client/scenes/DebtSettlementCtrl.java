
package client.scenes;

import java.util.List;
import java.util.ResourceBundle;

import client.utils.ServerUtils;
import commons.DebtCellData;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import com.google.inject.Inject;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DebtSettlementCtrl {
  @FXML
  private ListView<TitledPane> debtListView;
  @FXML
  private Text aboveCellText;
  @FXML
  private TitledPane debtCell;
  @FXML
  private Button sendEmailButton;
  @FXML
  private Button settleDebtButton;

  @FXML // fx:id="markReceivedButton"
  private Button markReceivedButton; // Value injected by FXMLLoader

  @FXML // fx:id="optionPane"
  private ListView<TitledPane> openDebts; // Value injected by FXMLLoader

  @FXML // fx:id="sendReminderButton"
  private Button sendReminderButton; // Value injected by FXMLLoader

  @FXML // fx:id="titleLabel"
  private Label titleLabel; // Value injected by FXMLLoader

  @FXML // fx:id="goBackButton"
  private Button goBackButton; // Value injected by FXMLLoader
  private final ServerUtils server;
  private final MainCtrl mainCtrl;
  /**
   * Constructor for the debt settlement controller.
   * @param mainCtrl - reference to the main controller
   */
  @Inject
  public DebtSettlementCtrl(MainCtrl mainCtrl, ServerUtils server) {
    this.mainCtrl = mainCtrl;
    this.server = server;

  }
  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    assert sendEmailButton != null : "fx:id=\"sendEmailButton\" was not injected: check your FXML file 'OpenDebts.fxml'.";
    assert settleDebtButton != null : "fx:id=\"settleDebtButton\" was not injected: check your FXML file 'OpenDebts.fxml'.";
    assert markReceivedButton != null : "fx:id=\"markReceivedButton\" was not injected: check your FXML file 'OpenDebts.fxml'.";
    assert sendReminderButton != null : "fx:id=\"sendReminderButton\" was not injected: check your FXML file 'OpenDebts.fxml'.";
    assert titleLabel != null : "fx:id=\"titleLabel\" was not injected: check your FXML file 'OpenDebts.fxml'.";
  }

  /**
   * depicts the debts that are still open on the debt list View in the debt-overview.
   */
  public void populateOpenDebts(){
    List<DebtCellData> debts = server.getOpenDebts(mainCtrl.getCurrentEventID());
    for(DebtCellData debt : debts){
      TextArea textArea = new TextArea(
              "Account holder: " + debt.getReceiver().getFirstName() + " " + debt.getReceiver().getLastName() + "\n"
                      + "IBAN: " + debt.getReceiver().getIban() + "\n");
      TitledPane titledPane = getTitledPane(debt, textArea);
      debtListView.getItems().add(titledPane);
    }
  }

  /**
   * returns the debtCellPane but titled with the correct text/body.
   * @param debt debtcellData filled with the debt of a person.
   * @param textArea textArea of the debt cell.
   * @return return a titledPane with the correct text/naming and debt amount
   */
  private static TitledPane getTitledPane(DebtCellData debt, TextArea textArea) {
    Button markReceived = new Button("Mark Received");
    Button sendEmail = new Button("Send Email");
    Button settleDebt = new Button("Settle Debt");
    HBox hBox = new HBox(markReceived, sendEmail, settleDebt);
    VBox vBox = new VBox(hBox, textArea);
    String text = new String(
            debt.getSender() + "should give "  + debt.getDebt() + "€"
            + "to " + debt.getReceiver());
    AnchorPane anchorPane = new AnchorPane(vBox);
    return new TitledPane(text, anchorPane);
  }

  public void setLanguageText(ResourceBundle resourceBundle) {

  }

  /**
   * Settles debt.
   */
  private void settleDebt(DebtCellData debtToSettle){

  }
  /**
   * Method to go back to the event page.
   */
  public void goBack() {
    mainCtrl.showEventPage(mainCtrl.getCurrentEventID());
  }

}