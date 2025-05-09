package it.ifts.ifoa.teletubbies;


import it.ifts.ifoa.teletubbies.repository.UserRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static spark.Spark.*;

public class App
{

    public static void main(String[] args)
    {
        port(8080);

        Connection connection;

        //todo: configure connection string
        try
        {
            connection = DriverManager.getConnection("");
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        UserRepository userRepository = new UserRepository(connection);



        post("/", (req, res) ->
        {
            return "";
        });
    }
}