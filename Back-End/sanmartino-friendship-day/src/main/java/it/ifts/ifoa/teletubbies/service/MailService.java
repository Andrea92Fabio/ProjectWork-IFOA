package it.ifts.ifoa.teletubbies.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class MailService {

    public static void sendEmail(String receiver) {
        final String senderEmail = "teletubbies.pw@gmail.com";
        final String password = "rzmw gkis qngy magn";

        String host = "smtp.gmail.com";
        int port = 587;

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");


        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAduthentication(){
            return new PasswordAuthentication(senderEmail, password);
           }
        });

        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(receiver));
            message.setSubject("Oggetto della mail");
            message.setText("Corpo della mail");
            Transport.send(message);
        } catch (MessagingException e){
            e.printStackTrace();
        }

    }

}
