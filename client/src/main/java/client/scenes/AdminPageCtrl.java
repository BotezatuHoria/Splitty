package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import jakarta.inject.Inject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;
import java.util.Optional;

public class AdminPageCtrl {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;


    @FXML
    private TableView<Event> events;

    @FXML
    private TableColumn<Event, String> titleColumn;
    @FXML
    private TableColumn<Event, Date> creationDateColumn;
    @FXML
    private TableColumn<Event, Date> lastModifiedColumn;

    @FXML
    private Button deleteEvent;

    @FXML
    private Button downloadEvent;

    @FXML
    private Button importEvent;

    @FXML
    private Label selectedEvent;

    /**
     * Constructor for the admin page controller.
     * @param server the server
     * @param mainCtrl the main controller
     */
    @Inject
    public AdminPageCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Gets all the events from the server and adds them to the tableview.
     */
    public void showEvents(){
        ObservableList<Event> items = FXCollections.observableArrayList();
        items.addAll(server.getEvents());
        events.setItems(items);
    }
    @FXML
    void initialize(){
        //set up the columns in the table
        titleColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("title"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<Event, Date>("creationDate"));
        lastModifiedColumn.setCellValueFactory(new PropertyValueFactory<Event, Date>("lastModified"));
        showEvents();
        changeSelectedEvent();
    }

    /**
     * Changes the selected event label to the event title
     */
    void changeSelectedEvent(){
        events.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Event>() {
            @Override
            public void changed(ObservableValue<? extends Event> observable, Event oldValue, Event newValue) {
                if (newValue != null){
                    selectedEvent.setText(newValue.getTitle());
                }
            }
        });
    }

    public void delete(){
        Event event = events.getSelectionModel().getSelectedItem();
        if (event == null){
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setContentText("Are you sure you want to delete event: " + event.getTitle());
        alert.showAndWait().filter(ButtonType.OK::equals).ifPresent(b -> {
            server.deleteEventById(event, event.getId());
            showEvents();
        });
        alert.close();
    }

    public void goBack() {
        mainCtrl.showAdminLogin();
    }


}
