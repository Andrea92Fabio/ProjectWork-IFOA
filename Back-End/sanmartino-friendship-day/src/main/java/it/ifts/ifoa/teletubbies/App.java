package it.ifts.ifoa.teletubbies;


import com.google.gson.Gson;
import it.ifts.ifoa.teletubbies.controller.SubmissionsController;
import it.ifts.ifoa.teletubbies.repository.UserRepository;
import it.ifts.ifoa.teletubbies.service.MailService;
import it.ifts.ifoa.teletubbies.service.UserSubmissionService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static spark.Spark.*;

public class App
{
    Connection connection;
    UserRepository userRepository;
    MailService mailService;
    SubmissionsController submissionsController;
    UserSubmissionService userSubmissionService;


    public static void main(String[] args)
    {
        new App().run();
    }


    public App()
    {
        port(8080);

        //todo: setup connection string
        try
        {
            this.connection = DriverManager.getConnection("");
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        this.userRepository = new UserRepository(connection);

        this.mailService = new MailService();
        this.userSubmissionService = new UserSubmissionService(userRepository);


        Gson gson = new Gson();
        this.submissionsController = new SubmissionsController(gson, userSubmissionService);
    }

    private void run()
    {
        submissionsController.initSubmissionEndpoint();
    }
}