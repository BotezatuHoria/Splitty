package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;

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
        showEvents();
    }

    public void goBack() {
        mainCtrl.showAdminLogin();
    }


}
