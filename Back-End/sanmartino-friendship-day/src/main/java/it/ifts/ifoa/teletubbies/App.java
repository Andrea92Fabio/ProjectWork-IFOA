package it.ifts.ifoa.teletubbies;


import static spark.Spark.*;

public class App
{
    public static void main(String[] args)
    {
        port(8080);
        post("/", (req, res) -> {
            return "";
        });
    }
}