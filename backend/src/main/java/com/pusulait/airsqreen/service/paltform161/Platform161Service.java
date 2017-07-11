package com.pusulait.airsqreen.service.paltform161;

import com.codahale.metrics.annotation.Timed;
import com.pusulait.airsqreen.domain.campaign.Campaign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by benan on 7/9/2017.
 */

@Slf4j
@Service
public class Platform161Service {

    private String appID;
    private String clientID;
    private String clientSecret;
    private String user;
    private String password;
    private String authEndPoint;
    private String campaignsEndPoint;

    @Inject
    private Environment env;

    @PostConstruct
    public void init() {

        this.appID = env.getProperty("platform161.api.appID");
        this.clientID = env.getProperty("platform161.api.clientID");
        this.clientSecret = env.getProperty("platform161.api.clientSecret");
        this.user = env.getProperty("platform161.api.user");
        this.password = env.getProperty("platform161.api.password");
        //End points
        this.authEndPoint = env.getProperty("platform161.endpoints.auth");
        this.campaignsEndPoint = env.getProperty("platform161.endpoints.campaigns");
        //getAuthToken();
    }

    @Timed
    public String getAuthToken() {
        String result = null;
        String url = this.authEndPoint;

        AuthRequestJson requestJson = new AuthRequestJson();

        requestJson.setUser(this.user);
        requestJson.setPassword(this.password);
        requestJson.setClientId(this.clientID);
        requestJson.setClientSecret(this.clientSecret);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType("application", "json"));
        HttpEntity<AuthRequestJson> requestEntity = new HttpEntity<AuthRequestJson>(requestJson, requestHeaders);

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        ResponseEntity<AuthResponseJson> responseEntity = null;

        try {

            responseEntity = restTemplate.postForEntity(url, requestEntity, AuthResponseJson.class);

             if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                AuthResponseJson res = responseEntity.getBody();
                if (res.getToken() != null) {
                    result = res.getToken();
                }
            }

        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }




    public List<Campaign> getCampaign(String token) {
        List<Campaign> campaigns = null;
        String url = this.campaignsEndPoint;

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType("application", "json"));
        requestHeaders.set("PFM161-API-AccessToken", token);
        HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", requestHeaders);

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        ResponseEntity<List<Campaign>> responseEntity = null;

        try {
            ParameterizedTypeReference<List<Campaign>> responseType = new ParameterizedTypeReference<List<Campaign>>() {};
            responseEntity =  restTemplate.exchange(url, HttpMethod.GET, requestEntity , responseType);

            if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                campaigns = responseEntity.getBody();
            }

        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }

        return campaigns;
    }


}
