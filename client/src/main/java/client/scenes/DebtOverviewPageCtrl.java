package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Person;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;

import java.util.List;

public class DebtOverviewPageCtrl {

  @FXML
  private TableView<Person> debtValueTable;
  @FXML
  private TableColumn<Person, String> name;
  @FXML
  private TableColumn<Person, Double> debt;
  @FXML
  private Button goBackButton;
  @FXML
  private Button settleDebtButton;
  @FXML
  private MainCtrl mainCtrl; // Reference to the MainController
  @FXML
  private final ServerUtils server;

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
  public void initialize() {
    this.name = new TableColumn<>("Name");
    this.debt = new TableColumn<>("Debt");
    name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
    debt.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getDebt()).asObject());
    debtValueTable.getColumns().clear();
    debtValueTable.getColumns().addAll(name, debt);

    // Set a row factory to listen for mouse click events on each row
    debtValueTable.setRowFactory(tv -> {
      TableRow<Person> row = new TableRow<>();
      row.setOnMouseClicked(event -> {
        if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
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
    debtValueTable.getItems().addAll(people);
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
}