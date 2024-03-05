/**
 * Sample Skeleton for 'StarterPage.fxml' Controller Class
 */

package client.scenes;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import commons.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.inject.Inject;

public class StarterPageCtrl {

    private final MainCtrl mainCtrl;

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

    @FXML // fx:id="languageSelector"
    private Button languageSelector; // Value injected by FXMLLoader

    @FXML // fx:id="listView"
    private ListView<Event> listView;

    /**
     * Constructor for the StarterPageCtrl class.
     * @param mainCtrl - the main controller
     */
    @Inject
    public StarterPageCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @FXML
    void selectLanguage() {
        mainCtrl.showLanguage();
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


    /**
     * Method that changes the primary stage to the event page.
     */
    public void showEventPage() {
        mainCtrl.showEventPage();
    }

    /**
     * should set the scene to a responsive event page with corresponding title and code etc.
     */
    public void showResponsiveEventPage(){
        String name = createTextField.getText();
        //here we should eventually make it so the title of our event gets posted.
        // Wanted to work on this, but then realised it should represent a half-finished, not even made event
        // with the title that should be just before the create button on the starterpage, maybe something we do later.
    }
}
