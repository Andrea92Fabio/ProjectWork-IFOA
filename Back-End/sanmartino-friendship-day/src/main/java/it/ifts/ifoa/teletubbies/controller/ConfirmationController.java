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
        get("/api/confirmation/:tokenId", (req, res) ->
        {
            String tokenId = req.params("tokenId");
            System.out.println(tokenId);
            if (this.userConfirmationService.confirmTokenIdAndCheckWin(tokenId))
            {
                return "You win";
                }
            return "You lose";
        });

    }
}
