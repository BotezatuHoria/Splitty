package client.scenes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client.utils.LanguageSingleton;
import client.utils.ServerUtils;
import commons.DebtCellData;
import commons.Person;
import commons.Transaction;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import com.google.inject.Inject;
import javafx.scene.text.Text;
import javafx.stage.Modality;

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
  /**
   * Method to initialize the debt settlement controller.
   */
  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    assert sendEmailButton != null : "fx:id=\"sendEmailButton\" was not injected: check your FXML file 'OpenDebts.fxml'.";
    assert settleDebtButton != null : "fx:id=\"settleDebtButton\" was not injected: check your FXML file 'OpenDebts.fxml'.";
    assert markReceivedButton != null : "fx:id=\"markReceivedButton\" was not injected: check your FXML file 'OpenDebts.fxml'.";
    assert sendReminderButton != null : "fx:id=\"sendReminderButton\" was not injected: check your FXML file 'OpenDebts.fxml'.";
    assert titleLabel != null : "fx:id=\"titleLabel\" was not injected: check your FXML file 'OpenDebts.fxml'.";
    server.registerForMessages("/topic/events/people", Person.class, person -> {
      Platform.runLater(() -> {
        clear();
        allDebts();
      });
    });
    mainCtrl.handleEnterKeyPress(goBackButton, this::goBack);
  }
  /**
   * Populates the open debts list.
   */
  public void debtsOfPerson(int idPerson){
    List<DebtCellData> debts = server.getDebtsOfPerson(mainCtrl.getCurrentEventID(), idPerson);
    populateOpenDebts(debts);
  }
  public void allDebts(){
    List<DebtCellData> debts = server.getOpenDebts(mainCtrl.getCurrentEventID());
    populateOpenDebts(debts);
  }
  /**
   * Populates the open debts list.
   */
  public void populateOpenDebts(List<DebtCellData> debts){
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
      titledPane.expandedProperty().set(false);
      debtListView.getItems().add(titledPane);
    }
  }

  /**
   * returns the debtCellPane but titled with the correct text/body.
   * @param debt debtCellData filled with the debt of a person.
   * @param textArea textArea of the debt cell.
   * @return return a titledPane with the correct text/naming and debt amount
   */
  private TitledPane getTitledPane(DebtCellData debt, TextArea textArea) {
    HBox hBox = createButtonBox(debt);
    VBox vBox = new VBox(10, hBox, textArea); // 10 is the spacing between elements
    vBox.getStyleClass().add("debt-vbox");
    String text = (LanguageSingleton.getInstance().getResourceBundle().getString("string.format"));
    String title = String.format(text, debt.getSender(), debt.getDebt(), debt.getReceiver());
    AnchorPane anchorPane = new AnchorPane(vBox);
    anchorPane.getStyleClass().add("debt-anchor-pane");
    return new TitledPane(title, anchorPane);
  }

  /**
   * Creates a button box.
   * @param debt debtCellData filled with the debt of a person.
   * @return returns a HBox with the buttons.
   */
  private HBox createButtonBox(DebtCellData debt) {
    String markReceivedString = (LanguageSingleton.getInstance().getResourceBundle().getString("mark.button"));
    String sendEmailString = (LanguageSingleton.getInstance().getResourceBundle().getString("sendMail.button"));
    String settleDebtString = (LanguageSingleton.getInstance().getResourceBundle().getString("settle.button"));

    Button markReceived = new Button(markReceivedString);
    markReceived.getStyleClass().add("debt-button");
    markReceived.setOnAction(event -> settleDebt(debt));
    Button sendEmail = new Button(sendEmailString);
    sendEmail.getStyleClass().add("debt-button");

    String titleOfEvent = server.getEventByID(mainCtrl.getCurrentEventID()).getTitle();
    String subject = "You owe money in: " + titleOfEvent;
    String message = "Dear participant of " + titleOfEvent + ", \n This is a reminder of your debt that is still open in this event. \n" +
            "To repay your debt, you need to pay " + debt.getDebt() + " EUR" + " to " + debt.getReceiver().toString() + ".\n" +
            "If, by some mistake, this debt is incorrect or already paid; please check the app and contact " + debt.getReceiver().toString() + "." +
            "Note: this email was auto-generated, not made by the person that send you this themselves. If there are inconsistencies please contact the moderators of the application." +
            "\n\n" +
            "Best regards,\n" +
            "" +  ServerUtils.getConfig().getClientsEmailAddress()+ "\n";
    sendEmail.setOnAction(actionEvent -> {server.sendEmail(debt.getSender().getEmail(), subject,
            message);});


    Button settleDebt = new Button(settleDebtString);
    settleDebt.getStyleClass().add("debt-button");
    settleDebt.setOnAction(actionEvent -> {settleDebt(debt);});
    HBox hBox = new HBox(10, markReceived, sendEmail, settleDebt); // 10 is the spacing between buttons
    hBox.getStyleClass().add("debt-hbox");
    return hBox;
  }

  /**
   * clears the fields in the page.
   */
  public void clear() {
    debtListView.getItems().clear();
  }

  /**
   * sets all the text in the file to the correct language chosen by the user.
   * @param resourceBundle the language.
   */
  public void setLanguageText(ResourceBundle resourceBundle) {
    openDebtsLabel.setText(resourceBundle.getString("openDebts.text"));
  }

  /**
   * Settles debt.
   */
  private void settleDebt(DebtCellData debtToSettle){
      addTransaction(debtToSettle);
  }

  /**
   * adds a transaction the current event.
   * @param debt debt that is associated with this transaction (total cost).
   */
  public void addTransaction(DebtCellData debt) {
    try {
      Person payer = debt.getSender();
      Person payee = debt.getReceiver();
      double value = debt.getDebt();
      LocalDate date = LocalDate.now();
      int currency = 840;
      List<Person> participants = new ArrayList<>();
      participants.add(payee);
      String title = payer +  " " +
              LanguageSingleton.getInstance().getResourceBundle().getString("pays.misc") + " " + payee;
      String expenseType = null;
      Transaction transaction = new Transaction(title, date, value, currency, expenseType, participants, payer);
      transaction.setHandOff(true);
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
   * Method to go back to the event page.
   */
  public void goBack() {
    mainCtrl.showDebtOverviewPage();
  }

}