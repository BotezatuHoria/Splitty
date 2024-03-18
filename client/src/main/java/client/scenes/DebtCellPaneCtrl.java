package client.scenes;

import commons.DebtCellData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class DebtCellPaneCtrl extends ListCell<DebtCellData> {

  private int eventID;
  @FXML // fx:id="root"
  private Pane root;
  @FXML // fx:id="loader"
  private FXMLLoader loader;
  @FXML // fx:id="debtInformation"
  private Text debtInformation;
  @FXML // fx:id="furtherInfo"
  private Pane furtherInfo;
  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'DebtCellPane.fxml'.";
    assert loader != null : "fx:id=\"loader\" was not injected: check your FXML file 'DebtCellPane.fxml'.";
    assert debtInformation != null : "fx:id=\"debtInformation\" was not injected: check your FXML file 'DebtCellPane.fxml'.";
    assert furtherInfo != null : "fx:id=\"furtherInfo\" was not injected: check your FXML file 'DebtCellPane.fxml'.";
  }

  /**
   * Constructor for debt pane controller.
   */
  public DebtCellPaneCtrl() {
    // load the FXML file and set the controller
    loader = new FXMLLoader(getClass().getResource("DebtCellPane.fxml"));
    loader.setController(this);
    try {
      root = loader.load();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds and cell to the ListView.
   * @param item The new item for the cell.
   * @param empty whether this cell represents data from the list. If it
   *        is empty, then it does not represent any domain data, but is a cell
   *        being used to render an "empty" row.
   */
  @Override
  protected void updateItem(DebtCellData item, boolean empty) {
    super.updateItem(item, empty);

    if (empty || item == null) {
      // if the cell is empty, show nothing
      setText(null);
      setGraphic(null);
    } else {
      // if the cell is not empty, show the data
      // set the values and styles of the components using the item object
      debtInformation.setText(getInfoText(item));

      // add the root pane to the cell
      setText(null);
      setGraphic(root);

      // add the mouse click event handler to the root pane
      root.setOnMouseClicked(event -> {
        // toggle the expanded or collapsed state of the cell
        // use the prefHeightProperty and visibleProperty of the components
        // use animations or transitions to make the changes smoother
        if (root.getPrefHeight() == 50) {
          // if the cell is collapsed, expand it
          root.setPrefHeight(100);
          furtherInfo.setVisible(true);
        } else {
          // if the cell is expanded, collapse it
          root.setPrefHeight(50);
          furtherInfo.setVisible(false);
        }
      });
    }
  }

   /**
   * Helping method for the updateItem class that creates string with data to be displayed.
   * @param item Class containing data about transaction.
   * @return String representation of the transaction.
   */
   public String getInfoText(DebtCellData item) {
    StringBuilder generalDebtInfo = new StringBuilder();

    generalDebtInfo.append(item.getSender().getFirstName());
    generalDebtInfo.append(" gives ");
    generalDebtInfo.append(item.getSender().getDebt());
    generalDebtInfo.append(" euros to ");
    generalDebtInfo.append(item.getReceiver().getFirstName());

    return generalDebtInfo.toString();
   }

  /**
   * Setter for eventID
   * @param eventID for the current event
   */
  public void setEventID(int eventID) {
    this.eventID = eventID;
  }

}
