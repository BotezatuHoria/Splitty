package client.scenes;

import client.utils.LanguageSingleton;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Person;
import commons.Transaction;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

public class DebtOverviewPageCtrl implements Initializable {

  @FXML
  private TableView<Person> debtValueTable;
  @FXML
  private TableColumn<Person, String> name;
  @FXML
  private TableColumn<Person, String> debt;
  @FXML
  private Button goBackButton;
  @FXML
  private Button settleDebtButton;

  @FXML
  private Label debtOverviewLabel;
  @FXML
  private MainCtrl mainCtrl; // Reference to the MainController
  @FXML
  private final ServerUtils server;

  @FXML
  private Label sharePerPersonLabel;

  @FXML
  private Label sumValue;

  private static final DecimalFormat df = new DecimalFormat("0.00");

  private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

  /**
   * Constructor for the debt settlement controller.
   * @param mainCtrl - reference to the main controller
   */
  @Inject
  public DebtOverviewPageCtrl(MainCtrl mainCtrl, ServerUtils server) {
    this.mainCtrl = mainCtrl;
    this.server = server;

  }

  /**
   * Initialize the debt overview page.
   */
  @FXML
  public void initializeTable() {
    this.name = new TableColumn<>("Name");
    this.debt = new TableColumn<>("Debt");

    name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
    debt.setCellValueFactory(cellData -> new SimpleStringProperty(decimalFormat.format(cellData.getValue().getDebt())));
    debtValueTable.getColumns().clear();
    debtValueTable.getColumns().addAll(name, debt);

    // Set a row factory to listen for mouse click events on each row
    debtValueTable.setRowFactory(tv -> {
      TableRow<Person> row = new TableRow<>();
      row.setOnMouseClicked(event -> {
        if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
          Person clickedPerson = row.getItem();
          // Call the method to open the new page, passing the clicked person as a parameter
          openDebtsForSpecificPerson(clickedPerson);
        }
      });
      return row;
    });


    settleDebtButton.setOnAction(e -> buttonPressHandle());
    goBackButton.setOnAction(e -> mainCtrl.showEventPage(mainCtrl.getCurrentEventID()));
  }
  /**
   * Populate the debt list.
   */
  public void populateDebtList() {

    debtValueTable.getItems().clear();
    List<Person> people = server.getPeopleInCurrentEvent(mainCtrl.getCurrentEventID());
    List<Transaction> transactions = server.getTransactions(mainCtrl.getCurrentEventID());
    if (transactions != null) {
      double sum = 0.0;
      for (Transaction t : transactions) {
        if (!t.isHandOff()) {
          sum += t.getMoney();
        }
      }
      if (!people.isEmpty()) {
        sum /= people.size();
        sumValue.setText(df.format(sum) + " EUR");
      }
    }

    debtValueTable.getItems().addAll(people);
    name.setText(LanguageSingleton.getInstance().getResourceBundle().getString("nameInTable"));
    debt.setText(LanguageSingleton.getInstance().getResourceBundle().getString("debtInTable"));
    settleDebtButton.setText(LanguageSingleton.getInstance().getResourceBundle().getString("settleList.button"));
    debtOverviewLabel.setText(LanguageSingleton.getInstance().getResourceBundle().getString("debtOverview.title"));
    sharePerPersonLabel.setText(LanguageSingleton.getInstance().getResourceBundle().getString("share.person"));
  }

  /**
   * Open the debt page for a specific person.
   * @param person - the person to open the debt page for.
   */
  public void openDebtsForSpecificPerson(Person person) {
    mainCtrl.showDebtPageForSpecificPerson(person);
  }

  /**
   * Handle button press.
   */
  @FXML
  public void buttonPressHandle() {
    mainCtrl.showDebtPage();
  }

  @Override
  /**
   * initializes all the buttons to refer to the correct pages.
   */
  public void initialize(URL url, ResourceBundle resourceBundle) {
    mainCtrl.handleEnterKeyPress(settleDebtButton, this::buttonPressHandle);
    mainCtrl.handleEnterKeyPress(goBackButton, () -> {
      mainCtrl.showEventPage(mainCtrl.getCurrentEventID());
    });
    initializeTable();

    server.registerForMessages("/topic/events/people", Person.class, person -> {
      Platform.runLater(this::populateDebtList);
    });
  }
}