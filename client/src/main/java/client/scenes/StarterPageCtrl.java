/**
 * Sample Skeleton for 'StarterPage.fxml' Controller Class
 */

package client.scenes;

import java.net.URL;
import java.util.*;

import client.utils.EventsSingleton;
import client.utils.FlagListCell;
import client.utils.LanguageSingleton;
import client.utils.ServerUtils;
import commons.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import com.google.inject.Inject;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.apache.commons.lang3.tuple.Pair;

public class StarterPageCtrl {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="createButton"
    private Button createButton;

    @FXML // fx:id="createTextLabel"
    private Label createTextLabel;

    @FXML // fx:id="createTextField"
    private TextField createTextField;

    @FXML // fx:id="joinEventText"
    private Label joinEventText;

    @FXML // fx:id="joinButton"
    private Button joinButton;

    @FXML // fx:id="joinTextField"
    private TextField joinTextField;

    @FXML // fx:id="recentlyViewedText"
    private Label recentlyViewedText;

    @FXML // fx:id="languageSelector"
    private ComboBox languageSelector; // Value injected by FXMLLoader

    @FXML // fx:id="listView"
    private ListView<Event> listView;

    @FXML
    private Button adminbutton;

    /**
     * Constructor for the StarterPageCtrl class.
     *
     * @param mainCtrl - the main controller
     * @param server
     */
    @Inject
    public StarterPageCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        languageSelector.getItems().addAll(FlagListCell.getLanguages());

        // Responsible for setting the flags and changing languages
        languageSelector.setCellFactory(lv -> new FlagListCell());
        languageSelector.setButtonCell(new FlagListCell());
    }

    @FXML
    void adminLogin(){
        mainCtrl.showAdminLogin();
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
        String name = createTextField.getText();
        if(name.equals("")) {name = "New Event";}
        Event event = server.addEvent(new Event("", name, 0, "", new ArrayList<>(), new ArrayList<>()));
        EventsSingleton instance = EventsSingleton.getInstance();
        instance.addEvent(event);
        listView.getItems().add(event);
        mainCtrl.showEventPage(event.getId());
    }

    /**
     * Method that allows you to join an event using a code.
     */
    public void joinEvent() {
        String name = joinTextField.getText();
        // This needs to be decoded in the future use, method by Tom
        int eventId = translateShareCode(name);
        try {
            server.getEventByID(eventId);
            if (!listView.getItems().contains(server.getEventByID(eventId))) {
                listView.getItems().add(server.getEventByID(eventId));
            }
            mainCtrl.showEventPage(eventId);
        }
        catch (Exception e){
            // System.out.println("This event doesn't exist");
            mainCtrl.showAlert("This event doesn't exist.");
        }
    }

    /**
     * Translates the sharecode back to the id, needed to join an event via sharecode.
     * @param shareCode the sharecode to translate to the id.
     * @return the id of the event.
     */
    public int translateShareCode(String shareCode){
        //String hardCodedShareCode = inviteCode.getText();
        int size = shareCode.length();
        String result = "";
        for( int i =0; i < size; i++){
            int number = shareCode.charAt(i);
            number = number - 65;
            result += number;
        }
        int total = Integer.parseInt(result);
        total = total/3000929;
        System.out.println("Translated from the sharecode, eventID = " + total);
        return total;
    }

    public void joinEventsList() {
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2) {
                    try {
                        int eventId = listView.getSelectionModel().getSelectedItem().getId();
                        server.getEventByID(eventId);
                        mainCtrl.showEventPage(eventId);
                    }
                    catch (Error e) {
                        mainCtrl.showAlert("This event doesn't exist anymore!");
                    }
                }
            }
        });
    }

    public void updateLanguage() {
        // Show current language
        Pair<String, Image> currentLanguage = LanguageSingleton.getInstance().getLanguage();
        languageSelector.getSelectionModel().select(currentLanguage);
    }

    public void setLanguageText(ResourceBundle resourceBundle) {
        createTextLabel.setText(resourceBundle.getString("create.event"));
        createButton.setText(resourceBundle.getString("create.button"));
        joinButton.setText(resourceBundle.getString("join.button"));
        joinEventText.setText(resourceBundle.getString("join.event"));
        recentlyViewedText.setText(resourceBundle.getString("recently.viewed"));
    }

    public void keyPressed(KeyEvent e) {
        TextField source = (TextField) e.getSource();
        if (e.getCode() == KeyCode.ENTER) {
            if (source == createTextField) {
                showEventPage();
            } else if (source == joinTextField) {
                joinEvent();
            }
        }
    }

}
