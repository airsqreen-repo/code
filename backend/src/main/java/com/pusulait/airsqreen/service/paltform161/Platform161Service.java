package com.pusulait.airsqreen.service.paltform161;

import com.codahale.metrics.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Created by benan on 7/9/2017.
 */

@Slf4j
@Service
public class Platform161Service {

    private String appID;
    private String appSecret;
    private String apiKey;
    private String userID;
    private String authEndPoint;
    private String campaignsEndPoint;

    @Inject
    private Environment env;

    @PostConstruct
    public void init() {

        this.appID = env.getProperty("platform161.api.AppID");
        this.appSecret = env.getProperty("platform161.api.AppSecret");
        this.apiKey = env.getProperty("platform161.api.API_Key");
        this.userID = env.getProperty("platform161.api.UserID");
        //End points
        this.authEndPoint = env.getProperty("platform161.endpoints.Auth");
        this.campaignsEndPoint = env.getProperty("platform161.endpoints.campaigns");
        //getAuthToken();
    }

    @Timed
    public String getAuthToken() {
        String result = null;
        String url = this.authEndPoint;

        AuthRequestJson requestJson = new AuthRequestJson();

        requestJson.setApiKey(this.apiKey);
        requestJson.setAppId(this.appID);
        requestJson.setAppSecret(this.appSecret);
        requestJson.setUserId(this.userID);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType("application", "json"));
        HttpEntity<AuthRequestJson> requestEntity = new HttpEntity<AuthRequestJson>(requestJson, requestHeaders);

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        ResponseEntity<AuthResponseJson> responseEntity = null;

        try {

            responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, AuthResponseJson.class);
            if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                AuthResponseJson res = responseEntity.getBody();
                if (res.getSuccess()) {
                    result = res.getAccessToken();
                }
            }

        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }


}
