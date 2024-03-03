/**
 * Sample Skeleton for 'InviteSending.fxml' Controller Class
 */

package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


import java.awt.*;
import java.awt.datatransfer.Clipboard; //to set the clipboard of the user when clicking the copy button, in method CopyCode.
import java.awt.datatransfer.StringSelection; // Also in method CopyCode.
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
     *Constructor and therefore making the connection.
     * @param server the server to connect with.
     * @param mainCtrl
     */
    public InviteSendingCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * The method that, when called, should send emails with invites to the event in every mail that has been put in the MainInputField.
     */
    public void sendInvite(){
        String mails = MailInputField.getText();
        Scanner scanner = new Scanner(mails);
        List<String> listOfMails = new ArrayList<>();
        while (scanner.hasNext()){
            listOfMails.add(scanner.next());
        }
        for(String s:listOfMails){
            // here we add the protocol to send emails if we want, using a mail API like google or the Java one.
        }

    }

    public void copyCode(){
        String inviteCode = InviteCode.getText(); //the code of the event, pictured on the page (not yet made to be gathered from the database).
        StringSelection selection = new StringSelection(inviteCode); //make it a stringselection so that we can set the clipboard contents to it.
        Clipboard board = Toolkit.getDefaultToolkit().getSystemClipboard(); // get our clipboard.
        board.setContents(selection, selection); //copy the eventinvite code to our clipboard.
    }



}