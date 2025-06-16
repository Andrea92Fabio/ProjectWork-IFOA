package it.ifts.ifoa.teletubbies.controller;

import static spark.Spark.*;

import com.google.gson.*;
import it.ifts.ifoa.teletubbies.entity.User;
import it.ifts.ifoa.teletubbies.exception.CustomException;
import it.ifts.ifoa.teletubbies.exception.InvalidEmailException;
import it.ifts.ifoa.teletubbies.exception.InvalidFiscalCodeException;
import it.ifts.ifoa.teletubbies.service.UserSubmissionService;
import org.eclipse.jetty.http.HttpStatus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SubmissionsController
{
    private final Gson gson;
    private final UserSubmissionService userSubmissionService;

    public SubmissionsController(Gson gson, UserSubmissionService userSubmissionService)
    {
        this.gson = gson;
        this.userSubmissionService = userSubmissionService;
    }

    public void initSubmissionEndpoint()
    {
        post("/", (req, res) ->
        {
            return "";
        });

        post("/api/submission", (req, res) ->
        {
            Map<String, List<String>> responseBody = new LinkedHashMap<>();
            List<String> messages = new ArrayList<>();

            try
            {
                User candidate = gson.fromJson(req.body(), User.class);
                candidate.assignTokenId();
                candidate.checkUser();
                System.out.println(this.userSubmissionService.isEmailAlreadyTaken(candidate));
                if (this.userSubmissionService.isEmailAlreadyTaken(candidate))
                {
                    throw new InvalidEmailException("1x01");
                }
                if (candidate.getResidencyCountry().equalsIgnoreCase("Italia"))
                {
                    if (this.userSubmissionService.isFiscalCodeAlreadyTaken(candidate))
                    {
                        throw new InvalidFiscalCodeException("1x02");
                    }
                }
                this.userSubmissionService.saveUser(candidate);
            }
            catch (JsonSyntaxException e)
            {
                res.status(HttpStatus.BAD_REQUEST_400);
                messages.add("invalid json format");
            }
            catch (CustomException e)
            {
                res.status(HttpStatus.BAD_REQUEST_400);
                messages.add(e.getMessage());
            }

            if (messages.isEmpty()) //if there are no errors
            {
                messages.add("submission successful");
                responseBody.put("message", messages);
            } else {
                responseBody.put("error", messages);
            }

            return gson.toJson(responseBody);
        });
    }
}
