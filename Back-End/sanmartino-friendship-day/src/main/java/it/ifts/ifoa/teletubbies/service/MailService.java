package it.ifts.ifoa.teletubbies.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class MailService {

    public static void sendEmail(String receiver, String tokedId) {
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
        String address = "http://localhost:8080/result.html?"+tokedId;
        String body = """
                <html>
                <head>
                <style>
                .cta {
                    cursor: pointer;
                    background-color:  #ff5024;
                    color: #361008;
                    transition: all 300ms;
                    font-size: 1rem;
                    text-transform: uppercase;
                    font-weight: 600;
                    letter-spacing: 0.25ch;
                
                    border: none;
                    border-radius: 10rem;
                
                    padding: 1rem 2rem;
                }
                
                .cta:hover {
                    background-color: #6b200f;
                    color:  #ffd9ce;
                }
                </style>
                </head>
                <body>
                <h1>Conferma l'iscrizione</h1>
                <center>
                <a href='%s' class='cta'
                >Clicca qui per Confermare<
                /a>
                </body>
                </html>
                """.formatted(address);

        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(receiver));
            message.setSubject("Conferma iscrizione concorso Teletubbies x San Martino");
            message.setContent(body, "text/html");

            Transport.send(message);
        } catch (MessagingException e){
            e.printStackTrace();
        }

    }

}
