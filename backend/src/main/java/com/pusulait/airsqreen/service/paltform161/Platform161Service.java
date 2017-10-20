package com.pusulait.airsqreen.service.paltform161;

import com.pusulait.airsqreen.config.constants.ServiceConstants;
import com.pusulait.airsqreen.domain.dto.campaign.platform161.Plt161CampaignDTO;
import com.pusulait.airsqreen.domain.dto.campaign.platform161.Plt161SectionDTO;
import com.pusulait.airsqreen.domain.dto.publisher.PublisherDTO;
import com.pusulait.airsqreen.domain.enums.PlatformType;
import com.pusulait.airsqreen.domain.enums.ServiceType;
import com.pusulait.airsqreen.domain.integration.PlatformUser;
import com.pusulait.airsqreen.repository.plt161.PlatformUserRepository;
import com.pusulait.airsqreen.util.RestPlatfrom161Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
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

    @Autowired
    private PlatformUserRepository platformUserRepository;

    @Inject
    private Environment env;


    @PostConstruct
    public void init() {

        this.appID = env.getProperty("platform161.api.appID");
        this.clientID = env.getProperty("platform161.api.clientID");
        this.clientSecret = env.getProperty("platform161.api.clientSecret");
        this.authEndPoint = env.getProperty("platform161.endpoints.auth");
        this.servicesEndPoint = env.getProperty("platform161.endpoints.services");
    }

    public String getAuthToken(PlatformUser platformUser) {

        this.user = platformUser.getUsername();
        this.password = platformUser.getPassword();

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


    public List<Plt161CampaignDTO> getCampaignList(String token, PlatformUser platformUser) {

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

        for(Plt161CampaignDTO campaignDTO : campaignDTOList){
                campaignDTO.setPlatformUserId(platformUser.getId());
        }

        return campaignDTOList;
    }


    public List<Plt161CampaignDTO> getAllCampaigns() {

        List<PlatformUser> platformUserList = platformUserRepository.findByPlatformTypeAndServiceType(PlatformType.DSP, ServiceType.PLATFORM_161);
        List<Plt161CampaignDTO> plt161CampaignDTOList = new ArrayList<>();

        for (PlatformUser platformUser : platformUserList) {
             plt161CampaignDTOList.addAll(getCampaignList(getAuthToken(platformUser),platformUser));
        }
        return plt161CampaignDTOList;
    }

    public List<Plt161SectionDTO> getSections(String token) {
        List<Plt161SectionDTO> plt161SectionDTOList = null;
        String url = this.servicesEndPoint + ServiceConstants.SECTIONS;

        HttpHeaders requestHeaders = RestPlatfrom161Util.setHeader(token);
        HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", requestHeaders);

        RestTemplate restTemplate = RestPlatfrom161Util.prepareRestTemplate();
        ResponseEntity<List<Plt161SectionDTO>> responseEntity = null;

        try {
            ParameterizedTypeReference<List<Plt161SectionDTO>> responseType = new ParameterizedTypeReference<List<Plt161SectionDTO>>() {
            };
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);

            if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                plt161SectionDTOList = responseEntity.getBody();
            }

        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }

        return plt161SectionDTOList;
    }

    /*public List<Plt161SectionDTO> getAllSections() {
        return getSections(getAuthToken());
    }*/

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

   /* public List<PublisherDTO> getAllPublishers() {
        return getPublishers(getAuthToken());
    }*/

    public Plt161SectionDTO getSection(String token, Long sectionId, Long platformUserId) {

        if (token == null) {
            PlatformUser platformUser = platformUserRepository.findOne(platformUserId);
            token = getAuthToken(platformUser);
        }


        Plt161SectionDTO plt161SectionDTO = null;
        String url = this.servicesEndPoint + ServiceConstants.SECTIONS + "/" + sectionId;

        HttpHeaders requestHeaders = RestPlatfrom161Util.setHeader(token);
        HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", requestHeaders);

        RestTemplate restTemplate = RestPlatfrom161Util.prepareRestTemplate();
        ResponseEntity<Plt161SectionDTO> responseEntity = null;

        try {
            ParameterizedTypeReference<Plt161SectionDTO> responseType = new ParameterizedTypeReference<Plt161SectionDTO>() {
            };
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);

            if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                plt161SectionDTO = responseEntity.getBody();
            }

        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }

        return plt161SectionDTO;
    }


}
