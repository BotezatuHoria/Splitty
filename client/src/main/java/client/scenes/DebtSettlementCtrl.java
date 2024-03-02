/**
 * Sample Skeleton for 'OpenDebts.fxml' Controller Class
 */

package client.scenes;

import java.net.URL;
import java.util.ResourceBundle;

import commons.PersonTemporary;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

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

  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    assert detailsButton != null : "fx:id=\"detailsButton\" was not injected: check your FXML file 'OpenDebts.fxml'.";
    assert dropDownButton != null : "fx:id=\"dropDownButton\" was not injected: check your FXML file 'OpenDebts.fxml'.";
    assert markReceivedButton != null : "fx:id=\"markReceivedButton\" was not injected: check your FXML file 'OpenDebts.fxml'.";
    assert optionPane != null : "fx:id=\"optionPane\" was not injected: check your FXML file 'OpenDebts.fxml'.";
    assert sendReminderButton != null : "fx:id=\"sendReminderButton\" was not injected: check your FXML file 'OpenDebts.fxml'.";
    assert titleLabel != null : "fx:id=\"titleLabel\" was not injected: check your FXML file 'OpenDebts.fxml'.";

  }
  public void addOpenDebt() {
    PersonTemporary p =
            new PersonTemporary("idk", "Horia", "Botezatu", "2334", 1);
    CheckBox personCheck = new CheckBox(p.getFirstName() + " " + p.getLastName());
    peopleLIstView.getItems().add(personCheck);
  }

}