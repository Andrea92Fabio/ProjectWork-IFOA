package it.ifts.ifoa.teletubbies.controller;

import it.ifts.ifoa.teletubbies.service.UserConfirmationService;

import static spark.Spark.*;

public class ConfirmationController
{
    private final UserConfirmationService userConfirmationService;

    public ConfirmationController(UserConfirmationService userConfirmationService)
    {
        this.userConfirmationService = userConfirmationService;
    }


    public void initConfirmationEndpoint()
    {
        get("/api/confirmation/:key", (req, res) ->
        {
            String key = req.params("key");
            if (this.userConfirmationService.confirmKeyAndCheckWin(key))
            {
                return "You win";
                }
            return "You lose";
        });

    }
}
