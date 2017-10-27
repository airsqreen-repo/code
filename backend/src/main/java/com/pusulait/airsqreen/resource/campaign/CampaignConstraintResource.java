package com.pusulait.airsqreen.resource.campaign;


import com.codahale.metrics.annotation.Timed;
import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.dto.campaign.CampaignConstraintDTO;
import com.pusulait.airsqreen.domain.dto.error.ErrorDTO;
import com.pusulait.airsqreen.domain.dto.error.SystemErrorDTO;
import com.pusulait.airsqreen.domain.enums.ErrorType;
import com.pusulait.airsqreen.security.SecurityUtils;
import com.pusulait.airsqreen.service.SystemErrorService;
import com.pusulait.airsqreen.service.campaign.CampaignConstraintService;
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
public class CampaignConstraintResource {


    @Autowired
    private SystemErrorService systemErrorService;

    @Autowired
    private CampaignConstraintService campaignConstraintService;


    @Timed
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_CAMPAIGN_CONSTRAINT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCampaignConstraint(@RequestBody CampaignConstraintDTO campaignConstraintDTO) throws URISyntaxException {
        log.debug("REST request to save campaignConstraintDTO : {}", campaignConstraintDTO);
        try {
            if (campaignConstraintDTO.getId() != null) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("campaignConstraint", "idexists",
                        "A new campaignConstraint cannot already have an ID")).body(null);
            }
            CampaignConstraintDTO result = campaignConstraintService.save(campaignConstraintDTO);
            return ResponseEntity.created(new URI(Constants.URL_ADMIN_DEVICE + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert("campaignConstraint", result.getId().toString()))
                    .body(result);
        } catch (Exception ex) {
            systemErrorService.save(new SystemErrorDTO(ex.getMessage(), ErrorType.CREATE_DEVICE, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("campaignConstraintService.create", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

    /**
     * PATCH  api/admin/campaignConstraints -> Updates an existing campaignConstraint.
     */
    @Timed
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_CAMPAIGN_CONSTRAINT, method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCampaignConstraint(@RequestBody CampaignConstraintDTO campaignConstraintDTO) throws URISyntaxException {
        log.debug("REST request to update CampaignConstraint : {}", campaignConstraintDTO);
        if (campaignConstraintDTO.getId() == null) {
            return createCampaignConstraint(campaignConstraintDTO);
        }
        CampaignConstraintDTO result = campaignConstraintService.save(campaignConstraintDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("campaignConstraint", campaignConstraintDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  api/admin/campaignConstraints/:id ->  get the "id" campaignConstraint.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_CAMPAIGN_CONSTRAINT + "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CampaignConstraintDTO> getCampaignConstraintById(@PathVariable Long id) {
        log.debug("REST request to get CampaignConstraint : {}", id);
        CampaignConstraintDTO campaignConstraint = campaignConstraintService.findOne(id);
        return Optional.ofNullable(campaignConstraint).map(result -> new ResponseEntity<>(result, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * GET  api/admin/campaignConstraints -> getAll the  campaignConstraints.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_CAMPAIGN_CONSTRAINT, method = RequestMethod.GET)
    public ResponseEntity<?> getAllCampaignConstraints(@ApiParam Pageable pageable, PagedResourcesAssembler assembler) {
        log.debug("REST request to get getAllCampaignConstraintDTOs ");
        try {

            return new ResponseEntity<>(assembler.toResource(campaignConstraintService.findAll(pageable)), HttpStatus.OK);

        } catch (Exception ex) {

            systemErrorService.save(new SystemErrorDTO(ex.getMessage(), ErrorType.GET_ALL_DEVICES, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("campaignConstraintService.getAllCampaignConstraints", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

    /**
     * DELETE  api/admin/campaignConstraints/:id -> delete the "id" campaignConstraint.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_CAMPAIGN_CONSTRAINT + "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCampaignConstraint(@PathVariable Long id) {
        log.debug("REST request to delete CampaignConstraint : {}", id);
        campaignConstraintService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("campaignConstraint", id.toString())).build();
    }


}
