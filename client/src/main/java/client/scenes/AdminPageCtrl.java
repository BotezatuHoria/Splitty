package client.scenes;

import client.utils.LanguageSingleton;
import client.utils.ServerUtils;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import commons.Event;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class AdminPageCtrl {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    private ObservableList<Event> data;

    @FXML
    private TableView<Event> events;

    @FXML
    private TableColumn<Event, String> titleColumn;
    @FXML
    private TableColumn<Event, Date> creationDateColumn;
    @FXML
    private TableColumn<Event, Date> lastModifiedColumn;

    @FXML
    private Label adminPageLabel;

    @FXML
    private Label allEventsLabel;

    @FXML
    private Label selectedEventLabel;

    @FXML
    private Button backButton;


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
        data = FXCollections.observableList(new ArrayList<>());
    }

    /**
     * Gets all the events from the server and adds them to the tableview.
     */
    public void showEvents(){
        var e = server.getEvents();
        if (e != null){
            data = FXCollections.observableList(e);
            events.setItems(data);
        }
    }
    @FXML
    void initialize(){
        //set up the columns in the table
        titleColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("title"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<Event, Date>("creationDate"));
        lastModifiedColumn.setCellValueFactory(new PropertyValueFactory<Event, Date>("lastModified"));
        server.registerForMessages("/topic/event", Event.class, event -> {
            Platform.runLater(() -> {
                data.add(event);
                showEvents();
            });
        });
    }

    /**
     * Changes the selected event label to the event title.
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
        alert.setContentText(LanguageSingleton.getInstance().getResourceBundle().getString("confirm.deleteEvent") + event.getTitle());
        alert.showAndWait().filter(ButtonType.OK::equals).ifPresent(b -> {
            server.deleteEventById(event, event.getId());
            clear();
            showEvents();
        });
        alert.close();
    }

    public void goBack() {
        mainCtrl.showAdminLogin();
    }

    public void setLanguageText(ResourceBundle resourceBundle) {
        adminPageLabel.setText(resourceBundle.getString("admin.page.label"));
        allEventsLabel.setText(resourceBundle.getString("events.all.label"));
        selectedEventLabel.setText(resourceBundle.getString("selected.event"));
        titleColumn.setText(resourceBundle.getString("table.header.title"));
        creationDateColumn.setText(resourceBundle.getString("table.header.creationDate"));
        lastModifiedColumn.setText(resourceBundle.getString("table.header.lastActivity"));
        importEvent.setText(resourceBundle.getString("button.import"));
        deleteEvent.setText(resourceBundle.getString("button.delete"));
        backButton.setText(resourceBundle.getString("button.back"));
    }

    public void downloadEvent(){
        Event event = events.getSelectionModel().getSelectedItem();
        if (event == null){
            return;
        }
        //object mapper to write object to json
        ObjectMapper obj = new ObjectMapper().registerModule(new JavaTimeModule());
        ObjectWriter writer = obj.writer(new DefaultPrettyPrinter());

        //file chooser to let user choose download destination
        JFrame parent = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File(event.getTitle()+".json"));

        try{

            int returnValue = fileChooser.showOpenDialog(parent);
            if (returnValue == JFileChooser.APPROVE_OPTION){
                File saveFile = fileChooser.getSelectedFile();
                String jsonStr = obj.writeValueAsString(event);
                obj.writeValue(saveFile, event);
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void importEvent() {
        JFrame parent = new JFrame();
        JFileChooser chooser = new JFileChooser();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON Files", "json");
        chooser.setFileFilter(filter);
        int returnValue = chooser.showOpenDialog(parent);
        if (returnValue == JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            try {
                Event event = objectMapper.readValue(file, Event.class);
                System.out.println(event);
                server.addEvent(event);
                showEvents();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void clear() {
        events.getItems().clear();
    }

}
