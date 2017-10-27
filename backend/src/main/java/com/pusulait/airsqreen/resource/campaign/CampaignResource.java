package com.pusulait.airsqreen.resource.campaign;


import com.codahale.metrics.annotation.Timed;
import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.base.DataStatus;
import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.dto.campaign.CampaignDTO;
import com.pusulait.airsqreen.domain.dto.device.DeviceDTO;
import com.pusulait.airsqreen.domain.dto.error.ErrorDTO;
import com.pusulait.airsqreen.domain.dto.error.SystemErrorDTO;
import com.pusulait.airsqreen.domain.enums.ErrorType;
import com.pusulait.airsqreen.security.SecurityUtils;
import com.pusulait.airsqreen.service.campaign.CampaignConstraintService;
import com.pusulait.airsqreen.service.campaign.CampaignSectionService;
import com.pusulait.airsqreen.service.campaign.CampaignService;
import com.pusulait.airsqreen.service.SystemErrorService;
import com.pusulait.airsqreen.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api", produces = "application/hal+json")
@Slf4j
public class CampaignResource {


    @Autowired
    private SystemErrorService systemErrorService;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private CampaignSectionService campaignSectionService;

    @Autowired
    private CampaignConstraintService campaignConstraintService;


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
     * GET  api/admin/campaigns/search -> getAll the  campaigns.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_CAMPAIGN + Constants.URL_SEARCH , method = RequestMethod.GET)
    public ResponseEntity<?> search(@RequestParam(value="active", required=false) Boolean active, @ApiParam Pageable pageable, PagedResourcesAssembler assembler) {
        log.debug("REST request to get getAllGlobalCurrencyDTOs ");
        try {
            return new ResponseEntity<>(assembler.toResource(campaignService.search(active,pageable)), HttpStatus.OK);
        } catch (Exception ex) {
            systemErrorService.save(new SystemErrorDTO(ex.getMessage(), ErrorType.GET_ALL_CAMPAINGS, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("campaignService.search", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

    /**
     * PATCH  api/admin/campaigns -> Updates an existing campaign.
     */
    @Timed
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_CAMPAIGN, method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCampaign(@RequestBody CampaignDTO campaignDTO) throws URISyntaxException {
        log.debug("REST request to update Campaign : {}", campaignDTO);

        Campaign result = campaignService.save(campaignDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("campaign", result.getId().toString()))
                .body(result);
    }


    /**
     * GET  api/admin/campaigns/:id ->  get the "id" device.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_CAMPAIGN + "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CampaignDTO> geCampaignById(@PathVariable Long id) {
        log.debug("REST request to get Campaign : {}", id);
        CampaignDTO campaignDTO = campaignService.findOne(id);
        return Optional.ofNullable(campaignDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    /**
     * GET  api/admin/campaigns/:id/campaignSections -> getAll the  campaigns campaignSections.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_CAMPAIGN + "/{id}/campaignSections", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCampaignsSections(@PathVariable Long id) {
        log.debug("REST request to get getAllCampaignsSections ");
        try {
            return new ResponseEntity<>(campaignSectionService.findByCampaignId(id), HttpStatus.OK);
        } catch (Exception ex) {
            systemErrorService.save(new SystemErrorDTO(ex.getMessage(), ErrorType.GET_ALL_CAMPAINGS, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("campaignSectionService.findByCampaignId", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

    /**
     * GET  api/admin/campaigns/:id/campaignConstraints -> getAll the  campaigns campaignConstraints.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_CAMPAIGN + "/{id}/campaignConstraints", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCampaignsConstraints(@PathVariable Long id) {
        log.debug("REST request to get getAllCampaignsConstraints ");
        try {
            return new ResponseEntity<>(campaignConstraintService.findByCampaignId(id), HttpStatus.OK);
        } catch (Exception ex) {
            systemErrorService.save(new SystemErrorDTO(ex.getMessage(), ErrorType.GET_ALL_CAMPAINGS, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("campaignSectionService.findByCampaignId", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }


}
