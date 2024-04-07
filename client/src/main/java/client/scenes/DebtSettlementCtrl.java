package client.scenes;

import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import client.utils.LanguageSingleton;
import client.utils.ServerUtils;
import commons.DebtCellData;
import commons.Person;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import com.google.inject.Inject;
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

  @FXML
  private Label openDebtsLabel;

  @FXML // fx:id="markReceivedButton"
  private Button markReceivedButton; // Value injected by FXMLLoader

  @FXML // fx:id="optionPane"
  private ListView<TitledPane> openDebts; // Value injected by FXMLLoader

  @FXML // fx:id="sendReminderButton"
  private Button sendReminderButton; // Value injected by FXMLLoader
  @FXML
  private AnchorPane rootPane;
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
    rootPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/DebtSettlementStyle.css")).toExternalForm());
    server.registerForMessages("/topic/events/people", Person.class, person -> {
      clear();
      populateOpenDebts();
    });
  }

  /**
   * depicts the debts that are still open on the debt list View in the debt-overview.
   */
  public void populateOpenDebts(){
    List<DebtCellData> debts = server.getOpenDebts(mainCtrl.getCurrentEventID());
    LanguageSingleton lang = LanguageSingleton.getInstance();
    ResourceBundle messages = lang.getResourceBundle();
    for(DebtCellData debt : debts){
      String iban = debt.getReceiver().getIban();
      if (iban == null || iban.isEmpty()) {
        iban = messages.getString("iban.error.noInput");
      }else{
        iban = "IBAN: " + iban;
      }
      String accountHolderLabel = messages.getString("account.details");

      TextArea textArea = new TextArea(
              accountHolderLabel + debt.getReceiver().getFirstName() + " " + debt.getReceiver().getLastName() + "\n"
                       + iban + "\n");
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
    HBox hBox = createButtonBox();
    VBox vBox = new VBox(10, hBox, textArea); // 10 is the spacing between elements
    vBox.getStyleClass().add("debt-vbox");
    String text = (LanguageSingleton.getInstance().getResourceBundle().getString("string.format"));
    String title = String.format(text, debt.getSender(), debt.getDebt(), debt.getReceiver());
    AnchorPane anchorPane = new AnchorPane(vBox);
    anchorPane.getStyleClass().add("debt-anchor-pane");
    return new TitledPane(title, anchorPane);
  }

  private static HBox createButtonBox() {
    String markReceivedString = (LanguageSingleton.getInstance().getResourceBundle().getString("mark.button"));
    String sendEmailString = (LanguageSingleton.getInstance().getResourceBundle().getString("sendMail.button"));
    String settleDebtString = (LanguageSingleton.getInstance().getResourceBundle().getString("settle.button"));

    Button markReceived = new Button(markReceivedString);
    markReceived.getStyleClass().add("debt-button");
    Button sendEmail = new Button(sendEmailString);
    sendEmail.getStyleClass().add("debt-button");
    Button settleDebt = new Button(settleDebtString);
    settleDebt.getStyleClass().add("debt-button");
    HBox hBox = new HBox(10, markReceived, sendEmail, settleDebt); // 10 is the spacing between buttons
    hBox.getStyleClass().add("debt-hbox");
    return hBox;
  }

  public void clear() {
    debtListView.getItems().clear();
  }

  public void setLanguageText(ResourceBundle resourceBundle) {
    openDebtsLabel.setText(resourceBundle.getString("openDebts.text"));
    goBackButton.setText(resourceBundle.getString("back.button"));
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