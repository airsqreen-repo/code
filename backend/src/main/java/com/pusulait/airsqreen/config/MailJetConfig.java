package com.pusulait.airsqreen.config;

/**
 * Created by Notebook on 10.1.2017.
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MailJetConfig {

    @Value("${mailJet.fromEmail}")
    private String mailJetFromEmail;

    @Value("${mailJet.fromName}")
    private String mailJetFromName;

    @Value("${mailJet.apiKey}")
    private String mailJetApiKey;

    @Value("${mailJet.apiSecret}")
    private String mailJetApiSecret;

    @Value("${mailJet.baseUrl}")
    private String baseUrl;

    public MailJetConfig() {
    }

    public MailJetConfig(String mailJetFromEmail, String mailJetFromName, String mailJetApiKey, String mailJetApiSecret, String baseUrl) {
        this.mailJetFromEmail = mailJetFromEmail;
        this.mailJetFromName = mailJetFromName;
        this.mailJetApiKey = mailJetApiKey;
        this.mailJetApiSecret = mailJetApiSecret;
        this.baseUrl = baseUrl;
    }

    public String getMailJetFromEmail() {
        return mailJetFromEmail;
    }

    public String getMailJetFromName() {
        return mailJetFromName;
    }

    public String getMailJetApiKey() {
        return mailJetApiKey;
    }

    public String getMailJetApiSecret() {
        return mailJetApiSecret;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public String toString() {
        return "MailJetConfig{" +
                "mailJetFromEmail='" + mailJetFromEmail + '\'' +
                ", mailJetFromName='" + mailJetFromName + '\'' +
                ", mailJetApiKey='" + mailJetApiKey + '\'' +
                ", mailJetApiSecret='" + mailJetApiSecret + '\'' +
                ", mailJetApiSecret='" + baseUrl + '\'' +
                '}';
    }
}