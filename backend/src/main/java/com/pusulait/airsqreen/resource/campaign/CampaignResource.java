package com.pusulait.airsqreen.resource.campaign;


import com.codahale.metrics.annotation.Timed;
import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.service.CampaignService;
import com.pusulait.airsqreen.service.viewcount.ViewCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/api", produces = "application/hal+json")
@Slf4j
public class CampaignResource {


    @Autowired
    private CampaignService campaignService;


    @Timed
    @RequestMapping(value = Constants.URL_CAMPAIGN, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveCampaigns() throws URISyntaxException {

        campaignService.save();
        return new ResponseEntity<>("", HttpStatus.OK);

    }

    @Timed
    @RequestMapping(value = Constants.URL_CAMPAIGN, method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCampaigns() throws URISyntaxException {

        campaignService.updateCampaigns();
        return new ResponseEntity<>("", HttpStatus.OK);

    }

}
