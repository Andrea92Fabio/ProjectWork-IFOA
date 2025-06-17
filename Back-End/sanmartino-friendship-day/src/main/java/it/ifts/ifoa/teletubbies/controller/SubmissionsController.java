package it.ifts.ifoa.teletubbies.controller;

import static spark.Spark.*;

import com.google.gson.*;
import it.ifts.ifoa.teletubbies.entity.User;
import it.ifts.ifoa.teletubbies.exception.*;
import it.ifts.ifoa.teletubbies.service.MailService;
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

    public SubmissionsController(Gson gson, UserSubmissionService userSubmissionService, MailService mailService)
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
                messages = candidate.checkUser();
                System.out.println(this.userSubmissionService.isEmailAlreadyTaken(candidate));
                if (this.userSubmissionService.isEmailAlreadyTaken(candidate))
                {
                    throw new EmailAlreadyPresentException("1x01");
                }
                if (candidate.getResidencyCountry().equalsIgnoreCase("italy"))
                {
                    if (this.userSubmissionService.isFiscalCodeAlreadyTaken(candidate))
                    {
                        throw new FiscalCodeAlreadyPresentException("1x02");
                    }
                }
                if (messages.isEmpty()){
                    this.userSubmissionService.saveUser(candidate);
                    MailService.sendEmail(candidate.getEmail(), candidate.getTokenId());
                }
            }
            catch (JsonSyntaxException e)
            {
                res.status(HttpStatus.BAD_REQUEST_400);
                messages.add("invalid json format");
            }
            catch (FiscalCodeAlreadyPresentException | EmailAlreadyPresentException e){
                res.status(HttpStatus.CONFLICT_409);
                messages.add(e.getMessage());
            }
            catch (CustomException e)
            {
                res.status(HttpStatus.BAD_REQUEST_400);
                messages.add(e.getMessage());
            }

            if (messages.isEmpty()) //if there are no errors
            {
                res.status(HttpStatus.CREATED_201);
                messages.add("submission successful");
                responseBody.put("messages", messages);
            } else {
                responseBody.put("errors", messages);
            }

            return gson.toJson(responseBody);
        });
    }
}
