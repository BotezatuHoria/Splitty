package client.scenes;

import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import client.utils.ServerUtils;
import commons.DebtCellData;
import commons.Person;
import javafx.application.Platform;
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
    rootPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/DebtSettlementStyle.css")).toExternalForm());
    server.registerForMessages("/topic/events/people", Person.class, person -> {
      Platform.runLater(() -> {
        clear();
        allDebts();
      });
    });
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
    for(DebtCellData debt : debts){
      String iban = debt.getReceiver().getIban();
      if (iban == null || iban.isEmpty()) {
        iban = "User did not provide IBAN";
      }else{
        iban = "IBAN: " + iban;
      }
      TextArea textArea = new TextArea(
              "Account holder: " + debt.getReceiver().getFirstName() + " " + debt.getReceiver().getLastName() + "\n"
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
    String title = String.format("%s should give %.2f euros to %s", debt.getSender(), debt.getDebt(), debt.getReceiver());
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
    Button markReceived = new Button("Mark Received");
    markReceived.getStyleClass().add("debt-button");
    markReceived.setOnAction(event -> settleDebt(debt));
    Button sendEmail = new Button("Send Email");
    sendEmail.getStyleClass().add("debt-button");
    Button settleDebt = new Button("Settle Debt");
    settleDebt.getStyleClass().add("debt-button");
    HBox hBox = new HBox(10, markReceived, sendEmail, settleDebt); // 10 is the spacing between buttons
    hBox.getStyleClass().add("debt-hbox");
    return hBox;
  }

  public void clear() {
    debtListView.getItems().clear();
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
    mainCtrl.showDebtOverviewPage();
  }

}