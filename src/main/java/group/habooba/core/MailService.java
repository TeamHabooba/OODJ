package group.habooba.core;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailService {

    private final String smtpHost;
    private final int smtpPort;
    private final String appEmail;
    private final String appPassword;

    private final Session session;

    public MailService(String smtpHost,
                       int smtpPort,
                       String appEmail,
                       String appPassword) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.appEmail = appEmail;
        this.appPassword = appPassword;
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(appEmail, appPassword);
            }
        });
    }

    /**
     * Sends message to user
     */
    public void sendToUser(String userEmail, String subject, String text) throws MessagingException, UnsupportedEncodingException {
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(appEmail, "OODJ Assignment "));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
        msg.setSubject(subject);
        msg.setText(text); // можно заменить на HTML
        Transport.send(msg);
    }
}
