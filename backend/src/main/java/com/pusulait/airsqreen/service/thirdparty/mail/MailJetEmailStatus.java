package com.pusulait.airsqreen.service.thirdparty.mail;

import org.json.JSONArray;


/**
 * Created by ferhatyaban on 03.07.2017.
 */
public class MailJetEmailStatus {

    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";

    private final String to;
    private final String subject;
    private final String body;

    private String status;
    private String message;

    public MailJetEmailStatus(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public MailJetEmailStatus success(JSONArray message) {
        this.message = message.toString();
        this.status = SUCCESS;
        return this;
    }

    public MailJetEmailStatus error(String errorMessage) {
        this.status = ERROR;
        this.message = errorMessage;
        return this;
    }

    public boolean isSuccess() {
        return SUCCESS.equals(this.status);
    }

    public boolean isError() {
        return ERROR.equals(this.status);
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
    @Override
    public String toString() {
        return "MailJetEmailStatus{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}