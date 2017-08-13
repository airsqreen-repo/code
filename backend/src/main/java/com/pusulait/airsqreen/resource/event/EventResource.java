package com.pusulait.airsqreen.resource.event;


import com.codahale.metrics.annotation.Timed;
import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.service.CampaignService;
import com.pusulait.airsqreen.service.cron.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/api", produces = "application/hal+json")
@Slf4j
public class EventResource {


    @Autowired
    private EventService eventService;


    @Timed
    @RequestMapping(value = Constants.URL_EVENT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveCampaigns() throws URISyntaxException {

        eventService.generateSistem9Events();
        return new ResponseEntity<>("", HttpStatus.OK);

    }

}
