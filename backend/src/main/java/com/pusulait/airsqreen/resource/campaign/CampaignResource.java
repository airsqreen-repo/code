package com.pusulait.airsqreen.resource.campaign;


import com.codahale.metrics.annotation.Timed;
import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.dto.campaign.CampaignDTO;
import com.pusulait.airsqreen.domain.dto.error.ErrorDTO;
import com.pusulait.airsqreen.domain.dto.error.SystemErrorDTO;
import com.pusulait.airsqreen.domain.enums.ErrorType;
import com.pusulait.airsqreen.security.SecurityUtils;
import com.pusulait.airsqreen.service.campaign.CampaignService;
import com.pusulait.airsqreen.service.SystemErrorService;
import com.pusulait.airsqreen.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/api", produces = "application/hal+json")
@Slf4j
public class CampaignResource {


    @Autowired
    private SystemErrorService systemErrorService;

    @Autowired
    private CampaignService campaignService;

    /*@Timed
    @RequestMapping(value = Constants.URL_CAMPAIGN, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveCampaigns() throws URISyntaxException {

        campaignService.saveAll();
        return new ResponseEntity<>("saveAll campaign called", HttpStatus.OK);

    }
*/
    @Timed
    @RequestMapping(value = Constants.URL_CAMPAIGN, method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCampaigns() throws URISyntaxException {

        campaignService.updateCampaigns();
        return new ResponseEntity<>("update campaign called", HttpStatus.OK);

    }

    /**
     * GET  api/admin/campaigns -> getAll the  campaigns.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_CAMPAIGN, method = RequestMethod.GET)
    public ResponseEntity<?> getAllCampaigns(@ApiParam Pageable pageable, PagedResourcesAssembler assembler) {
        log.debug("REST request to get getAllCampaignDTOs ");
        try {

            return new ResponseEntity<>(assembler.toResource(campaignService.findAll(pageable)), HttpStatus.OK);

        } catch (Exception ex) {

            systemErrorService.save(new SystemErrorDTO(ex.getMessage(), ErrorType.GET_ALL_CAMPAINGS, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("campaignService.getAllCampaigns", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

    /**
     * PATCH  api/admin/campaigns -> Updates an existing campaign.
     */
    @Timed
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_CAMPAIGN, method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDevice(@RequestBody CampaignDTO campaignDTO) throws URISyntaxException {
        log.debug("REST request to update Campaign : {}", campaignDTO);

        Campaign result = campaignService.save(campaignDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("campaign", result.getId().toString()))
                .body(result);
    }


}
