package client.scenes;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.ListView;

import static org.junit.jupiter.api.Assertions.*;

class AddExpenseCtrlTest {

    private MainCtrl sut;

    @BeforeEach
    public void setup() {
        sut = new MainCtrl();
    }
    @Test
    void addParticipantToViewTest() {
        AddExpenseCtrl addExpenseCtrl = new AddExpenseCtrl(sut);
        addExpenseCtrl.addParticipantToView();
        addExpenseCtrl.addParticipantToView();
        addExpenseCtrl.addParticipantToView();
        for (Node n : addExpenseCtrl.getNodes()) {
            // add
        }
    }

    @Test
    void addAllParticipantsTest() {
    }

    @Test
    void clearInputsTest() {
    }

    @Test
    void addExpenseTest() {
    }

    @Test
    void getNodesTest() {
    }
}