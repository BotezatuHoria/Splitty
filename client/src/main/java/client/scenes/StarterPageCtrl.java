/**
 * Sample Skeleton for 'StarterPage.fxml' Controller Class
 */

package client.scenes;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client.utils.ServerUtils;
import commons.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class StarterPageCtrl {

    private final ServerUtils server;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="createButton"
    private Button createButton;

    @FXML // fx:id="createTextField"
    private TextField createTextField;

    @FXML // fx:id="joinButton"
    private Button joinButton;

    @FXML // fx:id="joinTextField"
    private TextField joinTextField;

    @FXML // fx:id="listView"
    private ListView<Event> listView;

    /**
     * Constructor for the StarterPageCtrl class.
     * @param server - the server.
     */
    public StarterPageCtrl(ServerUtils server) {
        this.server = server;
    }

    /**
     * Function to add to the listView item.
     */
    public void addListView() {
        List<Event> events = new ArrayList<>();
        for (Event e: events) {
            listView.getItems().add(e);
        }
    }

    /**
     * Delete method for the list view.
     * @param e - event to be deleted from the list view.
     * @return - the event that has been deleted.
     */
    public Event deleteListView(Event e) {
        if (e == null) {
            return null;
        }
        listView.getItems().remove(e);
        return e;
    }

    /**
     * Method to return the number of elements in the listView.
     * @return - the number of elements in the listView.
     */
    public int numberOfElements() {
        return listView.getItems().size();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert createButton != null :
                "fx:id=\"createButton\" was not injected: check your FXML file 'StarterPage.fxml'.";
        assert createTextField != null :
                "fx:id=\"createTextField\" was not injected: check your FXML file 'StarterPage.fxml'.";
        assert joinButton != null :
                "fx:id=\"joinButton\" was not injected: check your FXML file 'StarterPage.fxml'.";
        assert joinTextField != null :
                "fx:id=\"joinTextField\" was not injected: check your FXML file 'StarterPage.fxml'.";
        assert listView != null :
                "fx:id=\"listView\" was not injected: check your FXML file 'StarterPage.fxml'.";

    }

}
