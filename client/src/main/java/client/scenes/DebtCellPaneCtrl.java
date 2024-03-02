package client.scenes;

import commons.DebtCellData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.awt.*;

public class DebtCellPaneCtrl extends ListCell<DebtCellData> {
  @FXML // fx:id="root"
  private Pane root;
  @FXML // fx:id="loader"
  private FXMLLoader loader;
  @FXML // fx:id="debtInformation"
  private Text debtInformation;
  @FXML // fx:id="furtherInfo"
  private Pane furtherInfo;

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
   * @param empty whether or not this cell represents data from the list. If it
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
      StringBuilder generalDebtInfo = new StringBuilder();

      generalDebtInfo.append(item.getSender().getFirstName());
      generalDebtInfo.append(" gives ");
      generalDebtInfo.append(item.getSender().getDebt());
      generalDebtInfo.append(" euros to ");
      generalDebtInfo.append(item.getReceiver().getFirstName());

      debtInformation.setText(generalDebtInfo.toString());

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
}
