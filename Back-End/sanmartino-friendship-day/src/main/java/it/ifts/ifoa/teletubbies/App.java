package it.ifts.ifoa.teletubbies;


import com.google.gson.Gson;
import it.ifts.ifoa.teletubbies.controller.ConfirmationController;
import it.ifts.ifoa.teletubbies.controller.SubmissionsController;
import it.ifts.ifoa.teletubbies.middleware.Middleware;
import it.ifts.ifoa.teletubbies.repository.UserRepository;
import it.ifts.ifoa.teletubbies.service.MailService;
import it.ifts.ifoa.teletubbies.service.UserConfirmationService;
import it.ifts.ifoa.teletubbies.service.UserSubmissionService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static spark.Spark.*;

//Aggiungere Corpo della mail in MailService e oggetto della mail
//Scegliere se fare un corpo in html o semplice txt

public class App
{
    Connection connection;
    UserRepository userRepository;
    MailService mailService;
    SubmissionsController submissionsController;
    ConfirmationController confirmationController;
    UserSubmissionService userSubmissionService;
    UserConfirmationService userConfirmationService;


    public static void main(String[] args)
    {
        new App().run();
    }


    public App()
    {
        port(80);

        Middleware.enableCORS();

        //todo: setup connection string
        try
        {
            this.connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/san_martino_friendship_day?user=root");
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        this.userRepository = new UserRepository(connection);

        this.mailService = new MailService();
        this.userSubmissionService = new UserSubmissionService(userRepository);
        this.userConfirmationService = new UserConfirmationService(userRepository);


        Gson gson = new Gson();
        this.submissionsController = new SubmissionsController(gson, userSubmissionService);
        this.confirmationController = new ConfirmationController(gson, userConfirmationService);

    }

    private void run()
    {
        submissionsController.initSubmissionEndpoint();

        confirmationController.initConfirmationEndpoint();
    }
}