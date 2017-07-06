package com.pusulait.airsqreen.service.thirdparty.mail;

import com.pusulait.airsqreen.config.MailJetConfig;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Email;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ferhatyaban on 03.07.2017.
 */
@Service
@Slf4j
public class MailJetEmailService {


    @Autowired
    protected MailJetConfig config;

    public void overrideConfig(MailJetConfig config) {
        this.config = config;
    }

    public MailJetEmailStatus send(String to, String subject, String body) throws Exception  {
        try {

            MailjetClient client = new MailjetClient(
                    config.getMailJetApiKey(),
                    config.getMailJetApiSecret()
            );


            MailjetRequest request = new MailjetRequest(Email.resource)
                    .property(Email.FROMEMAIL, config.getMailJetFromEmail())
                    .property(Email.FROMNAME, config.getMailJetFromName())
                    .property(Email.SUBJECT, subject)
                    .property(Email.TEXTPART, new HtmlToPlainText().getPlainText(Jsoup.parse(body)))
                    .property(Email.HTMLPART, body)
                    .property(
                            Email.RECIPIENTS,
                            new JSONArray().put(new JSONObject().put("Email", to))
                    );

            MailjetResponse response = client.post(request);

            return new MailJetEmailStatus(to, subject, body).success(response.getData());

        } catch (MailjetSocketTimeoutException | MailjetException e) {
            return new MailJetEmailStatus(to, subject, body).error(e.getMessage());
        }
    }





    // forgot password

}