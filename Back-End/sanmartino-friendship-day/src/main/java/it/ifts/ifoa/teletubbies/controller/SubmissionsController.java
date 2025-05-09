package it.ifts.ifoa.teletubbies.controller;

import static spark.Spark.*;

public class SubmissionsController
{
    public void initSubmissionEndpoint()
    {
        post("/", (req, res) ->
        {
            return "";
        });
    }
}
