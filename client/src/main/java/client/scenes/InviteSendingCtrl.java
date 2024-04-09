/**
 * Sample Skeleton for 'InviteSending.fxml' Controller Class
 */

package client.scenes;

import client.utils.LanguageSingleton;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.awt.datatransfer.Clipboard; //to set the clipboard of the user when clicking the copy button, in method CopyCode.
import java.awt.datatransfer.StringSelection; // Also in method CopyCode.
import java.util.*;
import java.util.List;


public class InviteSendingCtrl{

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private static final String EMAIL_USERNAME = "group53.splitty@gmail.com";
    private static final String EMAIL_PASSWORD = "pigu txlq kfdl rwsq";

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
     * The method that, when called, should send emails with invites to the event in every mail that has been put in the MainInputField.
     */
    public void sendInvite(){
        String mails = mailInputField.getText();
        Scanner scanner = new Scanner(mails);
        List<String> listOfMails = new ArrayList<>();
        while (scanner.hasNext()){
            listOfMails.add(scanner.next());
        }
        for(String s : listOfMails){
            String doSomethingWithThis= s.toLowerCase();// here we add the protocol to send emails if we want, using a mail API like google or the Java one.
            sendEmail(s, "Invite to Splitty App", inviteCode.getText());
        }

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
     * Translates the sharecode back to the id, needed to join an event via sharecode.
     * @param shareCode the sharecode to translate to the id.
     * @return the id of the event.
     */
    public int translateShareCode(String shareCode){
        //String hardCodedShareCode = inviteCode.getText();
        int size = shareCode.length();
        String result = "";
        for( int i =0; i < size; i++){
            int number = shareCode.charAt(i);
            number = number - 65;
            result += number;
        }
        int total = Integer.parseInt(result);
        total = total/3000929;
        return total;
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

    public void setLanguageText(ResourceBundle resourceBundle) {
        copyInviteCodeButton.setText(resourceBundle.getString("copy.button"));
        copyText.setText(resourceBundle.getString("copy.text"));
        inviteText.setText(resourceBundle.getString("invite.text"));
        mailInputField.setPromptText(resourceBundle.getString("textfield.text"));
        cancel.setText(resourceBundle.getString("cancel.button"));
        sendInviteButton.setText(resourceBundle.getString("send.button"));
    }

    /**
     * button to go back to the corresponding eventpage.
     */
    public void cancelGoBack(){
        mainCtrl.showEventPage(mainCtrl.getCurrentEventID());
    }

    public void sendEmail(String to, String subject, String message) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.debug", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(EMAIL_USERNAME));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            Transport.send(mimeMessage);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }

}