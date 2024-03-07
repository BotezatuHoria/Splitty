/**
 * Sample Skeleton for 'OpenDebts.fxml' Controller Class
 */

package client.scenes;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import com.google.inject.Inject;

public class DebtSettlementCtrl {


  @FXML // ResourceBundle that was given to the FXMLLoader
  private ResourceBundle resources;

  @FXML // URL location of the FXML file that was given to the FXMLLoader
  private URL location;
  @FXML // fx:id="openDebtsList"
  private ListView<String> openDebtsList;

  @FXML // fx:id="detailsButton"
  private Label detailsButton; // Value injected by FXMLLoader

  @FXML // fx:id="dropDownButton"
  private Button dropDownButton; // Value injected by FXMLLoader

  @FXML // fx:id="markReceivedButton"
  private Button markReceivedButton; // Value injected by FXMLLoader

  @FXML // fx:id="optionPane"
  private Pane optionPane; // Value injected by FXMLLoader

  @FXML // fx:id="sendReminderButton"
  private Button sendReminderButton; // Value injected by FXMLLoader

  @FXML // fx:id="titleLabel"
  private Label titleLabel; // Value injected by FXMLLoader

  @FXML // fx:id="goBackButton"
  private Button goBackButton; // Value injected by FXMLLoader

  private final MainCtrl mainCtrl;

  /**
   * Constructor for the debt settlement controller.
   * @param mainCtrl - reference to the main controller
   */
  @Inject
  public DebtSettlementCtrl(MainCtrl mainCtrl) {
    this.mainCtrl = mainCtrl;
  }
  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    assert detailsButton != null : "fx:id=\"detailsButton\" was not injected: check your FXML file 'OpenDebts.fxml'.";
    assert dropDownButton != null : "fx:id=\"dropDownButton\" was not injected: check your FXML file 'OpenDebts.fxml'.";
    assert markReceivedButton != null : "fx:id=\"markReceivedButton\" was not injected: check your FXML file 'OpenDebts.fxml'.";
    assert optionPane != null : "fx:id=\"optionPane\" was not injected: check your FXML file 'OpenDebts.fxml'.";
    assert sendReminderButton != null : "fx:id=\"sendReminderButton\" was not injected: check your FXML file 'OpenDebts.fxml'.";
    assert titleLabel != null : "fx:id=\"titleLabel\" was not injected: check your FXML file 'OpenDebts.fxml'.";

  }

  /**
   * Method to go back to the event page.
   */
  public void goBack() {
    mainCtrl.showEventPage();
  }

}