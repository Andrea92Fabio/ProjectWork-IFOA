package it.ifts.ifoa.teletubbies.middleware;

import static spark.Spark.*;

public class Middleware {
    public static void enableCORS(){
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*"); // use "*" only for dev
            response.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            response.header("Content-type", "application/json");
            System.out.println("cors");
        });

        // Optional: handle preflight (OPTIONS) requests
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

    }
}
