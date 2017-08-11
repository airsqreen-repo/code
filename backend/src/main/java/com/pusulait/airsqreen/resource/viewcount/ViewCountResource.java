package com.pusulait.airsqreen.resource.viewcount;

import com.codahale.metrics.annotation.Timed;
import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.dto.security.CampaignSectionDTO;
import com.pusulait.airsqreen.service.viewcount.ViewCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

/**
 * Created by benan on 8/6/2017.
 */
@RestController
@RequestMapping(value = "/api", produces = "application/hal+json")
@Slf4j
public class ViewCountResource {

    @Autowired
    private ViewCountService viewCountService;

    @Timed
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_VIEW_COUNT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CampaignSectionDTO> createToken(@RequestBody CampaignSectionDTO campaignSectionDTO) throws URISyntaxException {
        log.debug("REST request to save campaignSectionDTO : {}", campaignSectionDTO);

        String token = viewCountService.save(campaignSectionDTO.getCampaignId().toString(),campaignSectionDTO.getSectionId().toString());
        campaignSectionDTO.setTrackingToken(token);

        return new ResponseEntity<>(campaignSectionDTO, HttpStatus.OK);
    }

    @Timed
    @RequestMapping(value = Constants.URL_VIEW_COUNT , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> incrementViewCount(@RequestParam(value="token", required=true) String token) throws URISyntaxException {

        viewCountService.incrementViewCount(token);
        return new ResponseEntity<>("", HttpStatus.OK);

    }


}
