package com.pusulait.airsqreen.resource.campaign;


import com.codahale.metrics.annotation.Timed;
import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.dto.campaign.CampaignSectionDTO;
import com.pusulait.airsqreen.domain.dto.error.ErrorDTO;
import com.pusulait.airsqreen.domain.dto.error.SystemErrorDTO;
import com.pusulait.airsqreen.domain.enums.ErrorType;
import com.pusulait.airsqreen.security.SecurityUtils;
import com.pusulait.airsqreen.service.SystemErrorService;
import com.pusulait.airsqreen.service.campaign.CampaignSectionService;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api", produces = "application/hal+json")
@Slf4j
public class CampaignSectionResource {


    @Autowired
    private SystemErrorService systemErrorService;

    @Autowired
    private CampaignSectionService campaignSectionService;


    @Timed
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_CAMPAIGN_SECTION, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCampaignSection(@RequestBody CampaignSectionDTO campaignSectionDTO) throws URISyntaxException {
        log.debug("REST request to save campaignSectionDTO : {}", campaignSectionDTO);
        try {
            if (campaignSectionDTO.getId() != null) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("campaignSection", "idexists",
                        "A new campaignSection cannot already have an ID")).body(null);
            }
            CampaignSectionDTO result = campaignSectionService.save(campaignSectionDTO);
            return ResponseEntity.created(new URI(Constants.URL_ADMIN_DEVICE + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert("campaignSection", result.getId().toString()))
                    .body(result);
        } catch (Exception ex) {
            systemErrorService.save(new SystemErrorDTO(ex.getMessage(), ErrorType.CREATE_DEVICE, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("campaignSectionService.create", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

    /**
     * PATCH  api/admin/campaignSections -> Updates an existing campaignSection.
     */
    @Timed
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_CAMPAIGN_SECTION, method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCampaignSection(@RequestBody CampaignSectionDTO campaignSectionDTO) throws URISyntaxException {
        log.debug("REST request to update CampaignSection : {}", campaignSectionDTO);
        if (campaignSectionDTO.getId() == null) {
            return createCampaignSection(campaignSectionDTO);
        }
        CampaignSectionDTO result = campaignSectionService.save(campaignSectionDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("campaignSection", campaignSectionDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  api/admin/campaignSections/:id ->  get the "id" campaignSection.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_CAMPAIGN_SECTION + "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CampaignSectionDTO> getCampaignSectionById(@PathVariable Long id) {
        log.debug("REST request to get CampaignSection : {}", id);
        CampaignSectionDTO campaignSection = campaignSectionService.findOne(id);
        return Optional.ofNullable(campaignSection).map(result -> new ResponseEntity<>(result, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * GET  api/admin/campaignSections -> getAll the  campaignSections.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_CAMPAIGN_SECTION, method = RequestMethod.GET)
    public ResponseEntity<?> getAllCampaignSections(@ApiParam Pageable pageable, PagedResourcesAssembler assembler) {
        log.debug("REST request to get getAllCampaignSectionDTOs ");
        try {

            return new ResponseEntity<>(assembler.toResource(campaignSectionService.findAll(pageable)), HttpStatus.OK);

        } catch (Exception ex) {

            systemErrorService.save(new SystemErrorDTO(ex.getMessage(), ErrorType.GET_ALL_DEVICES, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("campaignSectionService.getAllCampaignSections", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

    /**
     * DELETE  api/admin/campaignSections/:id -> delete the "id" campaignSection.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_CAMPAIGN_SECTION + "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCampaignSection(@PathVariable Long id) {
        log.debug("REST request to delete CampaignSection : {}", id);
        campaignSectionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("campaignSection", id.toString())).build();
    }


}
