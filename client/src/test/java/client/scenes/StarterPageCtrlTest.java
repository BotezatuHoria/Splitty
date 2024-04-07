package client.scenes;

import client.utils.ServerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class StarterPageCtrlTest {
    MainCtrl mainCtrl;
    ServerUtils server;
    StarterPageCtrl starterPageCtrl;

    @BeforeEach
    void setup(){
        mainCtrl = mock(MainCtrl.class);
        server= mock(ServerUtils.class);
        starterPageCtrl = mock(StarterPageCtrl.class);
    }

    @Test
    void showEventPage() {
        starterPageCtrl.joinEvent();

    }

    @Test
    void translateShareCode() {
    }

    @Test
    void setLanguageText() {
    }
}