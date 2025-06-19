package it.ifts.ifoa.teletubbies.controller;

import static spark.Spark.*;

import com.google.gson.*;
import it.ifts.ifoa.teletubbies.entity.User;
import it.ifts.ifoa.teletubbies.exception.*;
import it.ifts.ifoa.teletubbies.service.MailService;
import it.ifts.ifoa.teletubbies.service.UserSubmissionService;
import it.ifts.ifoa.teletubbies.utils.SubmissionStatus;
import org.eclipse.jetty.http.HttpStatus;

import java.util.*;
import java.util.concurrent.ExecutorService;

public class SubmissionsController {
    private final Gson gson;
    private final UserSubmissionService userSubmissionService;
    private final ExecutorService emailExecutor;

    public SubmissionsController(Gson gson, UserSubmissionService userSubmissionService, MailService mailService, ExecutorService emailExecutor) {
        this.gson = gson;
        this.userSubmissionService = userSubmissionService;
        this.emailExecutor = emailExecutor;
    }

    public void initSubmissionEndpoint() {
        post("/", (req, res) -> {
            return "";
        });

        post("/api/submission", (req, res) -> {
            Map<String, List<String>> responseBody = new LinkedHashMap<>();
            List<String> messages = new ArrayList<>();

            try {
                System.out.println(req.body());
                User candidate = gson.fromJson(req.body(), User.class);
                candidate.assignTokenId();
                messages = candidate.checkUser();

                System.out.println(candidate);

                SubmissionStatus submissionStatus = SubmissionStatus.FIRST_REGISTRATION;

                Optional<Integer> idFromEmail = this.userSubmissionService.idFromEmail(candidate.getEmail());
                Optional<Integer> idFromFiscalCode;
                boolean isConfirmed;

                if (idFromEmail.isPresent()) {
                    submissionStatus = SubmissionStatus.ALREADY_PRESENT;

                    if (candidate.getResidencyCountry().equalsIgnoreCase("italy")) {
                        idFromFiscalCode = this.userSubmissionService.idFromFiscalCode(candidate.getFiscalCode());

                        if (idFromFiscalCode.isPresent()) {
                            if (idFromFiscalCode.get().equals(idFromEmail.get())) {
                                if (userSubmissionService.isEmailConfirmed(candidate.getEmail())) {
                                    submissionStatus = SubmissionStatus.ALREADY_CONFIRMED;
                                }
                            }
                            else {
                                submissionStatus = SubmissionStatus.INVALID;
                            }
                        }

                    }
                    else {
                        if (userSubmissionService.isEmailConfirmed(candidate.getEmail())) {
                            submissionStatus = SubmissionStatus.ALREADY_CONFIRMED;
                        }
                    }
                }

                else if (candidate.getResidencyCountry().equalsIgnoreCase("italy")) {

                    idFromFiscalCode = this.userSubmissionService.idFromFiscalCode(candidate.getFiscalCode());
                    if (idFromFiscalCode.isPresent()) {
                        submissionStatus = SubmissionStatus.INVALID;
                    }
                }
                if (messages.isEmpty()) {
                    if (submissionStatus == SubmissionStatus.FIRST_REGISTRATION) {
                        this.userSubmissionService.saveUser(candidate);
                    }
                    System.out.println(submissionStatus);
                    SubmissionStatus finalSubmissionStatus = submissionStatus;
                    emailExecutor.submit(() -> {
                        MailService.sendEmail(candidate.getEmail(), candidate.getTokenId(), finalSubmissionStatus);
                    });
                }
            }
            catch (JsonSyntaxException e) {
                res.status(HttpStatus.BAD_REQUEST_400);
                messages.add("invalid json format");
            }
            catch (FiscalCodeAlreadyPresentException | EmailAlreadyPresentException e) {
                res.status(HttpStatus.CONFLICT_409);
                messages.add(e.getMessage());
            }
            catch (CustomException e) {
                res.status(HttpStatus.BAD_REQUEST_400);
                messages.add(e.getMessage());
            }

            if (messages.isEmpty()) //if there are no errors
            {
                res.status(HttpStatus.CREATED_201);
                messages.add("submission successful");
                responseBody.put("messages", messages);
            }
            else {
                responseBody.put("errors", messages);
            }

            System.out.println(responseBody.get("errors"));
            System.out.println(responseBody.get("messages"));
            System.out.println(res.status());
            return gson.toJson(responseBody);
        });
    }
}
