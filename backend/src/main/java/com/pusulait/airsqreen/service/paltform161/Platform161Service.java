package com.pusulait.airsqreen.service.paltform161;

import com.codahale.metrics.annotation.Timed;
import com.pusulait.airsqreen.config.constants.ServiceConstants;
import com.pusulait.airsqreen.domain.dto.campaign.Plt161CampaignDTO;
import com.pusulait.airsqreen.domain.dto.campaign.InventorySourceDTO2;
import com.pusulait.airsqreen.domain.dto.publisher.PublisherDTO;
import com.pusulait.airsqreen.domain.dto.section.SectionDTO;
import com.pusulait.airsqreen.util.RestPlatfrom161Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

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
    private String servicesEndPoint;

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
        this.servicesEndPoint = env.getProperty("platform161.endpoints.services");

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

        RestTemplate restTemplate = RestPlatfrom161Util.prepareRestTemplate();
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


    public List<Plt161CampaignDTO> getCampaign(String token) {

        List<Plt161CampaignDTO> campaignDTOList = null;
        String url = this.servicesEndPoint + ServiceConstants.CAMPAIGNS;

        HttpHeaders requestHeaders = RestPlatfrom161Util.setHeader(token);
        HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", requestHeaders);

        RestTemplate restTemplate = RestPlatfrom161Util.prepareRestTemplate();
        ResponseEntity<List<Plt161CampaignDTO>> responseEntity = null;

        try {
            ParameterizedTypeReference<List<Plt161CampaignDTO>> responseType = new ParameterizedTypeReference<List<Plt161CampaignDTO>>() {
            };
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);

            if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                campaignDTOList = responseEntity.getBody();
            }

        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }

        return campaignDTOList;
    }

    public List<InventorySourceDTO2> getInventory(String token) {

        List<InventorySourceDTO2> inventoryDTOList = null;
        String url = this.servicesEndPoint + ServiceConstants.INVENTORIES;


        HttpHeaders requestHeaders = RestPlatfrom161Util.setHeader(token);
        HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", requestHeaders);

        RestTemplate restTemplate = RestPlatfrom161Util.prepareRestTemplate();
        ResponseEntity<List<InventorySourceDTO2>> responseEntity = null;

        try {
            ParameterizedTypeReference<List<InventorySourceDTO2>> responseType = new ParameterizedTypeReference<List<InventorySourceDTO2>>() {
            };
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);

            if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                inventoryDTOList = responseEntity.getBody();
            }

        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }

        return inventoryDTOList;
    }

    public List<Plt161CampaignDTO> getAllCampaigns() {
        return getCampaign(getAuthToken());

    }

    public List<InventorySourceDTO2> getAllInventories() {
        return getInventory(getAuthToken());

    }

    public List<SectionDTO> getSections(String token) {
        List<SectionDTO> sectionDTOList = null;
        String url = this.servicesEndPoint + ServiceConstants.SECTIONS;

        HttpHeaders requestHeaders = RestPlatfrom161Util.setHeader(token);
        HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", requestHeaders);

        RestTemplate restTemplate = RestPlatfrom161Util.prepareRestTemplate();
        ResponseEntity<List<SectionDTO>> responseEntity = null;

        try {
            ParameterizedTypeReference<List<SectionDTO>> responseType = new ParameterizedTypeReference<List<SectionDTO>>() {
            };
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);

            if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                sectionDTOList = responseEntity.getBody();
            }

        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }

        return sectionDTOList;
    }

    public List<SectionDTO> getAllSections() {
        return getSections(getAuthToken());
    }

    public List<PublisherDTO> getPublishers(String token) {
        List<PublisherDTO> publisherDTOList = null;
        String url = this.servicesEndPoint + ServiceConstants.PUBLISHERS;


        HttpHeaders requestHeaders = RestPlatfrom161Util.setHeader(token);
        HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", requestHeaders);

        RestTemplate restTemplate = RestPlatfrom161Util.prepareRestTemplate();
        ResponseEntity<List<PublisherDTO>> responseEntity = null;

        try {
            ParameterizedTypeReference<List<PublisherDTO>> responseType = new ParameterizedTypeReference<List<PublisherDTO>>() {
            };
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);

            if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                publisherDTOList = responseEntity.getBody();
            }

        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }

        return publisherDTOList;
    }

    public List<PublisherDTO> getAllPublishers() {
        return getPublishers(getAuthToken());
    }

    public SectionDTO getSection(String token,Long sectionId ) {

        if(token == null)
            token = getAuthToken();

        SectionDTO sectionDTO = null;
        String url = this.servicesEndPoint + ServiceConstants.SECTIONS + "/" + sectionId;;


        HttpHeaders requestHeaders = RestPlatfrom161Util.setHeader(token);
        HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", requestHeaders);

        RestTemplate restTemplate = RestPlatfrom161Util.prepareRestTemplate();
        ResponseEntity<SectionDTO> responseEntity = null;

        try {
            ParameterizedTypeReference<SectionDTO> responseType = new ParameterizedTypeReference<SectionDTO>() {
            };
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);

            if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                sectionDTO = responseEntity.getBody();
            }

        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }

        return sectionDTO;
    }




}
