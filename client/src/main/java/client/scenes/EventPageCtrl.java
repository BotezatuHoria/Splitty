/**
 * Sample Skeleton for 'EventPage.fxml' Controller Class
 */

package client.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class EventPageCtrl {
    private final MainCtrl mainCtrl;
    @FXML // fx:id="addExpense"
    private Button addExpense; // Value injected by FXMLLoader

    @FXML // fx:id="addParticipant"
    private Button addParticipant; // Value injected by FXMLLoader

    @FXML // fx:id="allExpenses"
    private Button allExpenses; // Value injected by FXMLLoader

    @FXML // fx:id="editParticipants"
    private Button editParticipants; // Value injected by FXMLLoader

    @FXML // fx:id="fromPartipant"
    private Button fromPartipant; // Value injected by FXMLLoader

    @FXML // fx:id="includingParticpant"
    private Button includingParticpant; // Value injected by FXMLLoader

    @FXML // fx:id="sendInvites"
    private Button sendInvites; // Value injected by FXMLLoader

    @FXML // fx:id="settleDebts"
    private Button settleDebts; // Value injected by FXMLLoader

    @Inject
    public EventPageCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    public void showAddExpensePage() {
        mainCtrl.showExpensePage();
    }

    public void showInvitePage(){
        mainCtrl.showInvitePage();
    }
}


