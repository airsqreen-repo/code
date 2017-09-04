package com.pusulait.airsqreen.resource.device;

import com.codahale.metrics.annotation.Timed;
import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.base.DataStatus;
import com.pusulait.airsqreen.domain.dto.device.DeviceConstraintDTO;
import com.pusulait.airsqreen.domain.dto.error.ErrorDTO;
import com.pusulait.airsqreen.domain.dto.error.SystemErrorDTO;
import com.pusulait.airsqreen.domain.enums.ErrorType;
import com.pusulait.airsqreen.security.SecurityUtils;
import com.pusulait.airsqreen.service.SystemErrorService;
import com.pusulait.airsqreen.service.device.DeviceConstraintService;
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
import java.util.List;
import java.util.Optional;

/**
 * Created by ferhat on 09/04/2017.
 */

@Slf4j
@RestController
@RequestMapping(value = "/api", produces = "application/hal+json")
public class DeviceConstraintResource {
    /**
     * REST controller for managing Device.
     */

    @Autowired
    private DeviceConstraintService deviceConstraintService;
    

    @Autowired
    private SystemErrorService systemErrorService;

    /**
     * POST  api/admin/deviceConstraints -> Create a new device.
     */
    @Timed
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_DEVICE_CONSTRAINTS, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDeviceConstraint(@RequestBody DeviceConstraintDTO deviceConstraintDTO) throws URISyntaxException {
        log.debug("REST request to save Device Constraint : {}", deviceConstraintDTO);
        try {
            if (deviceConstraintDTO.getId() != null) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("deviceConstraints", "idexists",
                        "A new device cannot already have an ID")).body(null);
            }
            DeviceConstraintDTO result = deviceConstraintService.save(deviceConstraintDTO);
            return ResponseEntity.created(new URI(Constants.URL_ADMIN_DEVICE_CONSTRAINTS + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert("deviceConstraints", result.getId().toString()))
                    .body(result);
        } catch (Exception ex) {
            systemErrorService.save(new SystemErrorDTO(ex.getMessage(), ErrorType.CREATE_DEVICE, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("deviceConstraintService.create", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

    /**
     * PATCH  api/admin/deviceConstraints -> Updates an existing device.
     */
    @Timed
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_DEVICE_CONSTRAINTS, method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDeviceConstraint(@RequestBody DeviceConstraintDTO deviceConstraintDTO) throws URISyntaxException {
        log.debug("REST request to update Device Constraint : {}", deviceConstraintDTO);
        if (deviceConstraintDTO.getId() == null) {
            return createDeviceConstraint(deviceConstraintDTO);
        }
        DeviceConstraintDTO result = deviceConstraintService.save(deviceConstraintDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("deviceConstraints", deviceConstraintDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  api/admin/deviceConstraints/:id ->  get the "id" device.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_DEVICE_CONSTRAINTS + "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeviceConstraintDTO> getDeviceConstraintById(@PathVariable Long id) {
        log.debug("REST request to get Device Constraint : {}", id);
        DeviceConstraintDTO device = deviceConstraintService.findOne(id);
        return Optional.ofNullable(device).map(result -> new ResponseEntity<>(result, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * GET  api/admin/deviceConstraints -> getAll the  devices.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_DEVICE_CONSTRAINTS, method = RequestMethod.GET)
    public ResponseEntity<?> getAllDeviceConstraints(@ApiParam Pageable pageable, PagedResourcesAssembler assembler) {
        log.debug("REST request to get getAllDeviceConstraintDTOs ");
        try {

            return new ResponseEntity<>(assembler.toResource(deviceConstraintService.findAll(pageable)), HttpStatus.OK);

        } catch (Exception ex) {

            systemErrorService.save(new SystemErrorDTO(ex.getMessage(), ErrorType.GET_ALL_DEVICES, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("deviceConstraintService.getAllDeviceConstraints", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

    /**
     * DELETE  api/admin/deviceConstraints/:id -> delete the "id" device.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_DEVICE_CONSTRAINTS + "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDeviceConstraint(@PathVariable Long id) {
        log.debug("REST request to delete Device Constraint : {}", id);
        deviceConstraintService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("deviceConstraints", id.toString())).build();
    }



}