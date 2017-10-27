package com.pusulait.airsqreen.resource.campaign;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.base.DataStatus;
import com.pusulait.airsqreen.domain.dto.campaign.SectionDTO;
import com.pusulait.airsqreen.domain.dto.error.ErrorDTO;
import com.pusulait.airsqreen.domain.dto.error.SystemErrorDTO;
import com.pusulait.airsqreen.domain.enums.ErrorType;
import com.pusulait.airsqreen.security.SecurityUtils;
import com.pusulait.airsqreen.service.SectionService;
import com.pusulait.airsqreen.service.SystemErrorService;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Created by benan on 5/29/2017.
 */

@Slf4j
@RestController
@RequestMapping(value = "/api", produces = "application/hal+json")
public class SectionResource {
    /**
     * REST controller for managing PlatformUser.
     */

    @Autowired
    private SectionService sectionService;

    @Autowired
    private SystemErrorService systemErrorService;

    /**
     * GET  api/admin/section/search/findByName -> get all findByName.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_SECTION + Constants.URL_SEARCH, method = RequestMethod.GET)
    public ResponseEntity<?> getFindByName(@RequestParam(value="name", required=false) String name, @RequestParam(value="dataStatus", required=false) DataStatus dataStatus, @ApiParam Pageable pageable, PagedResourcesAssembler assembler) {
        log.debug("REST request to get getFindByName ");
        try {
            final Page<SectionDTO> page = sectionService.search(name, dataStatus, pageable);
            return new ResponseEntity<>(assembler.toResource(page), HttpStatus.OK);
        } catch (Exception ex) {
            systemErrorService.save(new SystemErrorDTO(ex.getMessage(), ErrorType.FIND_SECTION, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("sectionService.findByName", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }




}