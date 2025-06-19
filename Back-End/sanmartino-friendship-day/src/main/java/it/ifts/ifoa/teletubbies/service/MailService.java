package it.ifts.ifoa.teletubbies.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class MailService {

    public static void sendEmail(String receiver, String tokedId, String body) {
        final String senderEmail = "teletubbies.pw@gmail.com";
        final String password = "rzmw gkis qngy magn";

        String subject = "Conferma iscrizione concorso Teletubbies x San Martino";
        String address = "http://192.168.100.37:8080/result.html?tokenId="+tokedId;

        String host = "smtp.gmail.com";
        int port = 587;

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(subject);
            //message.setText(body);
            message.setContent(body.formatted(address), "text/html");

            Transport.send(message);
            System.out.println("Email sent successfully to: " + receiver);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }


}
