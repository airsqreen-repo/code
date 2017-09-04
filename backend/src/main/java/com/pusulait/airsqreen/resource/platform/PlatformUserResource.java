package com.pusulait.airsqreen.resource.platform;

import com.codahale.metrics.annotation.Timed;
import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.base.DataStatus;
import com.pusulait.airsqreen.domain.dto.error.ErrorDTO;
import com.pusulait.airsqreen.domain.dto.error.SystemErrorDTO;
import com.pusulait.airsqreen.domain.dto.platform.PlatformUserDTO;
import com.pusulait.airsqreen.domain.enums.ErrorType;
import com.pusulait.airsqreen.security.SecurityUtils;
import com.pusulait.airsqreen.service.SystemErrorService;
import com.pusulait.airsqreen.service.platform.PlatformUserService;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * Created by benan on 5/29/2017.
 */

@Slf4j
@RestController
@RequestMapping(value = "/api", produces = "application/hal+json")
public class PlatformUserResource {
    /**
     * REST controller for managing PlatformUser.
     */

    @Autowired
    private PlatformUserService platformUserService;

    @Autowired
    private SystemErrorService systemErrorService;

    /**
     * POST  api/admin/platformUsers -> Create a new platformUser.
     */
    @Timed
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_PLATFORM_USER, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPlatformUser(@RequestBody PlatformUserDTO platformUserDTO) throws URISyntaxException {
        log.debug("REST request to save PlatformUser : {}", platformUserDTO);
        try {
            if (platformUserDTO.getId() != null) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("platformUser", "idexists",
                        "A new platformUser cannot already have an ID")).body(null);
            }
            PlatformUserDTO result = platformUserService.save(platformUserDTO);
            return ResponseEntity.created(new URI(Constants.URL_ADMIN_PLATFORM_USER + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert("platformUser", result.getId().toString()))
                    .body(result);
        } catch (Exception ex) {
            systemErrorService.save(new SystemErrorDTO(ex.getMessage(), ErrorType.CREATE_PLATFORM_USER, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("platformUserService.create", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

    /**
     * PATCH  api/admin/platformUsers -> Updates an existing platformUser.
     */
    @Timed
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_PLATFORM_USER, method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePlatformUser(@RequestBody PlatformUserDTO platformUserDTO) throws URISyntaxException {
        log.debug("REST request to update PlatformUser : {}", platformUserDTO);
        if (platformUserDTO.getId() == null) {
            return createPlatformUser(platformUserDTO);
        }
        PlatformUserDTO result = platformUserService.save(platformUserDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("platformUser", platformUserDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  api/admin/platformUsers/:id ->  get the "id" platformUser.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_PLATFORM_USER + "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PlatformUserDTO> getPlatformUserById(@PathVariable Long id) {
        log.debug("REST request to get PlatformUser : {}", id);
        PlatformUserDTO platformUser = platformUserService.findOne(id);
        return Optional.ofNullable(platformUser).map(result -> new ResponseEntity<>(result, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * GET  api/admin/platformUsers -> getAll the  platformUsers.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_PLATFORM_USER, method = RequestMethod.GET)
    public ResponseEntity<?> getAllPlatformUsers(@ApiParam Pageable pageable, PagedResourcesAssembler assembler) {
        log.debug("REST request to get getAllPlatformUserDTOs ");
        try {

            return new ResponseEntity<>(assembler.toResource(platformUserService.findAll(pageable)), HttpStatus.OK);

        } catch (Exception ex) {

            systemErrorService.save(new SystemErrorDTO(ex.getMessage(), ErrorType.GET_ALL_PLATFORM_USERS, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("platformUserService.getAllPlatformUsers", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

    /**
     * DELETE  api/admin/platformUsers/:id -> delete the "id" platformUser.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_PLATFORM_USER + "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePlatformUser(@PathVariable Long id) {
        log.debug("REST request to delete PlatformUser : {}", id);
        platformUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("platformUser", id.toString())).build();
    }


    /**
     * GET  api/admin/platformUsers/search/findByUsernameAndStatus -> get all findByNameAndStatus platformUsers.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_PLATFORM_USER + Constants.URL_SEARCH + "/findByUsernameAndStatus", method = RequestMethod.GET)
    public ResponseEntity<?> getFindByNameAndStatus(@RequestParam(value="username", required=false) String username, @RequestParam(value="dataStatus", required=false) DataStatus dataStatus, @ApiParam Pageable pageable, PagedResourcesAssembler assembler) {
        log.debug("REST request to get getAllGlobalCurrencyDTOs ");
        try {
            final Page<PlatformUserDTO> page = platformUserService.findByUsernameAndStatus(username, dataStatus, pageable);
            return new ResponseEntity<>(assembler.toResource(page), HttpStatus.OK);
        } catch (Exception ex) {
            systemErrorService.save(new SystemErrorDTO(ex.getMessage(), ErrorType.GET_ALL_PLATFORM_USERS, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("platformUserService.getFindByNameAndStatus", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }


    /**
     * GET  api/v1/platformUsers/:id -> get the "id" platformUser.
     */
    @RequestMapping(value = Constants.URL_V1 + Constants.URL_PLATFORM_USER + "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PlatformUserDTO> getPlatformUser(@PathVariable Long id) {
        log.debug("REST request to get PlatformUser : {}", id);
        PlatformUserDTO platformUser = platformUserService.findOne(id);
        return Optional.ofNullable(platformUser).map(result -> new ResponseEntity<>(result, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}