package client.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;

import javax.xml.crypto.Data;

public class DebtCellPaneCtrl extends ListCell<Data> {

  private Pane root;
  private FXMLLoader loader;

  public DebtCellPaneCtrl() {
    // load the FXML file and set the controller
    loader = new FXMLLoader(getClass().getResource("cell.fxml"));
    loader.setController(this);
    try {
      root = loader.load();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void updateItem(Data item, boolean empty) {
    super.updateItem(item, empty);

    if (empty || item == null) {
      // if the cell is empty, show nothing
      setText(null);
      setGraphic(null);
    } else {
      // if the cell is not empty, show the data
      // you can access the components in the FXML file using @FXML annotations
      // you can also set the values and styles of the components using the item object
      // for example:
      // titleLabel.setText(item.getTitle());
      // infoLabel.setText(item.getInfo());
      // imageView.setImage(item.getImage());

      // add the root pane to the cell
      setText(null);
      setGraphic(root);

      // add the mouse click event handler to the root pane
      root.setOnMouseClicked(event -> {
        // toggle the expanded or collapsed state of the cell
        // you can use the prefHeightProperty and visibleProperty of the components
        // you can also use animations or transitions to make the changes smoother
        // for example:
        // if (root.getPrefHeight() == 50) {
        //     root.setPrefHeight(100);
        //     infoLabel.setVisible(true);
        // } else {
        //     root.setPrefHeight(50);
        //     infoLabel.setVisible(false);
        // }
      });
    }
  }
}
