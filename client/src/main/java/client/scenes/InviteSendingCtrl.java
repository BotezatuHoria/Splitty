/**
 * Sample Skeleton for 'InviteSending.fxml' Controller Class
 */

package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class InviteSendingCtrl{

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML // fx:id="CopyInviteCodeButton"
    private Button CopyInviteCodeButton; // Value injected by FXMLLoader

    @FXML // fx:id="EventTitle"
    private Label EventTitle; // Value injected by FXMLLoader

    @FXML // fx:id="InviteCode"
    private Label InviteCode; // Value injected by FXMLLoader

    @FXML // fx:id="MailInputField"
    private TextArea MailInputField; // Value injected by FXMLLoader

    @FXML // fx:id="SendInviteButton"
    private Button SendInviteButton; // Value injected by FXMLLoader

    /**
     *very great thing i need to finish.
     * @param server the server to connect with
     * @param mainCtrl
     */
    public InviteSendingCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void sendInvite() {
        String mails = MailInputField.getText();
        Scanner scanner = new Scanner(mails);
        List<String> listOfMails = new ArrayList<>();
        while (scanner.hasNext()){
            listOfMails.add(scanner.next());
        }
        for(String s:listOfMails){
            String recepient = s;
            String sender = "t.p.p.vanLeest@student.tudelft.nl";
        }


    }



}