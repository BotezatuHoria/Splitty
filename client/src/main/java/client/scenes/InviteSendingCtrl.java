/**
 * Sample Skeleton for 'InviteSending.fxml' Controller Class
 */

package client.scenes;

import client.utils.LanguageSingleton;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Person;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.awt.datatransfer.Clipboard; //to set the clipboard of the user when clicking the copy button, in method CopyCode.
import java.awt.datatransfer.StringSelection; // Also in method CopyCode.
import java.net.URL;
import java.util.*;
import java.util.List;


public class InviteSendingCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML // fx:id="CopyInviteCodeButton"
    private Button copyInviteCodeButton; // Value injected by FXMLLoader

    @FXML // fx:id="EventTitle"
    private Label eventTitle; // Value injected by FXMLLoader

    @FXML // fx:id="InviteCode"
    private Label inviteCode; // Value injected by FXMLLoader

    @FXML // fx:id="MailInputField"
    private TextArea mailInputField; // Value injected by FXMLLoader

    @FXML // fx:id="SendInviteButton"
    private Button sendInviteButton; // Value injected by FXMLLoader

    @FXML // fx:id="copyText"
    private Label copyText; // Value injected by FXMLLoader

    @FXML // fx:id="inviteText"
    private Label inviteText; // Value injected by FXMLLoader

    @FXML // fx:id="cancel"
    private Button cancel; // Value injected by FXMLLoader

    @FXML // fx:id="responseCopy"
    private Label responseCopy; // Value injected by FXMLLoader

    @FXML
    private Button checkButton;

    @FXML
    private Label checkLabel;

    /**
     *Constructor and therefore making the connection.
     * @param server the server to connect with.
     * @param mainCtrl main control page to connect with.
     */
    @Inject
    public InviteSendingCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;

    }

    /**
     * The method that, when called, sends emails with invites to the event in every mail that has been put in the MainInputField.
     */
    public void sendInvite(){
        String mails = mailInputField.getText();
        Scanner scanner = new Scanner(mails);
        List<String> listOfMails = new ArrayList<>();
        List<Person> list = server.getEventByID(mainCtrl.getCurrentEventID()).getPeople();
        String titleOfEvent = server.getEventByID(mainCtrl.getCurrentEventID()).getTitle();
        String listOfPeople = "";
        for (int i = 0; i < list.size(); i ++){
            if(i == list.size() - 1){
                listOfPeople += list.get(i);
            }
            else{
                listOfPeople += list.get(i) + ", ";
            }
        }

        String message = "Dear invited,\n" +
                "\n" +
                "I am excited to invite you to join me on Splitty for an upcoming event called \""+ titleOfEvent + "\" \n" +
                "With splitty, we can organize and manage events and transactions." +
                "\n" +
                "Here are the details of the event:\n" +
                "\n" +
                "Event Name: " + titleOfEvent + "\n" +
                "Persons that already joined: "+ listOfPeople+ "\n" +
                "To join the event, simply follow these steps:\n" +
                "\n" +
                "Download the Splitty app from the App Store or Google Play Store if you haven't already.\n" +
                "Once started, choose a language and color palette, then navigate to the Events joining page and enter the event code: "+ inviteCode.getText()+ ".\n" +
                "You will now be able to access all the details of the event and start making transactions with other participants, " +
                "as well as adding, editing and inviting new people to the event.\n" +
                "\n" +
                "I look forward to seeing you at "+ titleOfEvent + ".\n" +
                "Note: this email is auto generated. When in doubt, try to connect the persons inviting you.\n" +
                "If this mail is not destined for you, don't use it.\n" +
                "\n" +
                "Best regards,\n" +
                "" +  ServerUtils.getConfig().getClientsEmailAddress()+ "\n";

        String subject = "Invite to the Splitty event: " + titleOfEvent;

        while (scanner.hasNext()){
            listOfMails.add(scanner.next());
        }
        for(String s : listOfMails){
            // here we add the protocol to send emails if we want, using a mail API like google or the Java one.
            server.sendEmail(s, subject, message);
        }

    }


    /**
     * sends an email to the email-address that is configured in the config file. If the email is not received, then the credentials are wrong.
     */
    public void sendCheck(){
        String message = "Dear user of Splitty,\n" +
                "\n" +
                "This email is an confirmation email: by receiving this email, you are sure to have your credentials correct." +
                "\n" +
                "If by some mistake this mail is not of your doing, then that means that a user of this app filled in the wrong credentials (and had yours)." +
                "\n" +
                "Our excuses for this inconvenience; you should just ignore this email." +
                "\n" +
                "Note: this is an auto-generated email.\n" +
                "\n" +
                "Best regards (to yourself)\n" +
                "" +  ServerUtils.getConfig().getClientsEmailAddress()+ "\n";

        String subject = "Confirmation of credentials email";
        String address = ServerUtils.getConfig().getClientsEmailAddress();

            // here we add the protocol to send emails if we want, using a mail API like google or the Java one.
            server.sendEmail(address, subject, message);
    }

    /**
     * sets the info of the check button visible.
     */
    public void setCheckLabel(){
        ResourceBundle resourceBundle = LanguageSingleton.getInstance().getResourceBundle();
        checkLabel.setText(resourceBundle.getString("check.label"));
    }
    /**
     * sets the info of the check button invisible.
     */
    public void unSetCheckLabel(){
        checkLabel.setText("");
    }



    /**
     * Sets the title of the event on the invitepage.
     */
    public void setEventTitle(){
        String title = server.getEventByID(mainCtrl.getCurrentEventID()).getTitle();
        eventTitle.setText(title);

    }

    /**
     * Sets the share code of the event, an encoded version of the id.
     */
    public void setShareCode(){
        Event event = server.getEventByID(mainCtrl.getCurrentEventID());
        inviteCode.setText(event.getToken());
    }

    /**
     * Method for copying the share code to share with friends.
     */
    public void copyCode() throws InterruptedException {
        setShareCode();
        String inviteCode = this.inviteCode.getText();
        StringSelection selection = new StringSelection(inviteCode); //make it a stringselection so that we can set the clipboard contents to it.
        Clipboard board = Toolkit.getDefaultToolkit().getSystemClipboard(); // get our clipboard.
        board.setContents(selection, selection); //copy the eventinvite code to our clipboard.
        ResourceBundle resourceBundle = LanguageSingleton.getInstance().getResourceBundle();

        responseCopy.setText(resourceBundle.getString("message.code.copied"));
    }

    /**
     * sets the language of al the items that need it on the whole page.
     * @param resourceBundle the recourseBundle with the correct language.
     */
    public void setLanguageText(ResourceBundle resourceBundle) {
        copyInviteCodeButton.setText(resourceBundle.getString("copy.button"));
        copyText.setText(resourceBundle.getString("copy.text"));
        inviteText.setText(resourceBundle.getString("invite.text"));
        mailInputField.setPromptText(resourceBundle.getString("textfield.text"));
        sendInviteButton.setText(resourceBundle.getString("send.button"));
        checkButton.setText(resourceBundle.getString("check.button"));
    }

    /**
     * button to go back to the corresponding eventpage.
     */
    public void cancelGoBack(){
        mainCtrl.showEventPage(mainCtrl.getCurrentEventID());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}