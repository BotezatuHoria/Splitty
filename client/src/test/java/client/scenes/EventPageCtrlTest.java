package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class EventPageCtrlTest {

    EventPageCtrl eventPageCtrl;
    MainCtrl mainCtrl;
    ServerUtils server;

    @BeforeEach
    void setup(){
        eventPageCtrl = mock(EventPageCtrl.class);
        mainCtrl = mock(MainCtrl.class);
        server = mock(ServerUtils.class);

        Event event = new Event(null, "title", 1, null, null, null);

    }

    @Test
    void setTitle() {
        eventPageCtrl.setTitle();
    }

    @Test
    void editName() {
    }

    @Test
    void saveName() {
    }

    @Test
    void selectParticipant() {
    }

    @Test
    void displayTransactions() {
    }

    @Test
    void displayFrom() {
    }

    @Test
    void displayIncluding() {
    }

    @Test
    void clear() {
    }
}