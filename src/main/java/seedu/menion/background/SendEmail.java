package seedu.menion.background;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Scanner;

import javax.crypto.SealedObject;
import javax.mail.*;
import javax.mail.internet.*;

import seedu.menion.logic.commands.RemindCommand;

//@@author A0139164A -reused

public class SendEmail {

    final String senderEmailID = "menioncena@gmail.com";
    final String senderPassword = "johncena2103";
    final String emailSMTPserver = "smtp.gmail.com";
    final String emailServerPort = "465";

    String receiverEmailID = null;
    String emailSubject = null;
    String emailBody = null;
    boolean remindOn = false;

    // public void send(ReadOnlyActivity dub) {

    public void send(String receiverEmailID, String emailSubject, String emailBody) {

        // Retrieve the email of the user from the txt file.
        try {
            Scanner fromFile = new Scanner(new File(RemindCommand.MESSAGE_FILE));
            String userEmail = fromFile.nextLine();
            fromFile.close(); // close input file stream
        } catch (FileNotFoundException fnfe) {
            return; // If no file found. Return.
        }
        
        this.receiverEmailID = receiverEmailID;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;

        Properties props = new Properties();
        props.put("mail.smtp.user", senderEmailID);
        props.put("mail.smtp.host", emailSMTPserver);
        props.put("mail.smtp.port", emailServerPort);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        // props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.socketFactory.port", emailServerPort);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        SecurityManager security = System.getSecurityManager();

        try {
            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);
            // session.setDebug(true);

            MimeMessage msg = new MimeMessage(session);
            msg.setText(emailBody);
            msg.setSubject(emailSubject);
            msg.setFrom(new InternetAddress(senderEmailID));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmailID));
            Transport.send(msg);
        } catch (Exception mex) {
            mex.printStackTrace();
        }

    }

    public class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(senderEmailID, senderPassword);
        }
    }
}