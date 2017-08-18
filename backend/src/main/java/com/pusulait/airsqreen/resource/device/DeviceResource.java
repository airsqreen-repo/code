package com.pusulait.airsqreen.resource.device;

import com.codahale.metrics.annotation.Timed;
import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.dto.device.DeviceDTO;
import com.pusulait.airsqreen.domain.dto.error.ErrorDTO;
import com.pusulait.airsqreen.domain.dto.error.SystemErrorDTO;
import com.pusulait.airsqreen.domain.enums.ErrorType;
import com.pusulait.airsqreen.security.SecurityUtils;
import com.pusulait.airsqreen.service.SystemErrorService;
import com.pusulait.airsqreen.service.device.DeviceService;
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

/**
 * Created by benan on 5/29/2017.
 */

@Slf4j
@RestController
@RequestMapping(value = "/api", produces = "application/hal+json")
public class DeviceResource {
    /**
     * REST controller for managing Device.
     */

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SystemErrorService systemErrorService;

    /**
     * POST  api/admin/devices -> Create a new device.
     */
    @Timed
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_DEVICE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDevice(@RequestBody DeviceDTO deviceDTO) throws URISyntaxException {
        log.debug("REST request to save Device : {}", deviceDTO);
        try {
            if (deviceDTO.getId() != null) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("device", "idexists",
                        "A new device cannot already have an ID")).body(null);
            }
            DeviceDTO result = deviceService.save(deviceDTO);
            return ResponseEntity.created(new URI(Constants.URL_ADMIN_DEVICE + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert("device", result.getId().toString()))
                    .body(result);
        } catch (Exception ex) {
            systemErrorService.save(new SystemErrorDTO(ex.getMessage(), ErrorType.CREATE_DEVICE, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("deviceService.create", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

    /**
     * PATCH  api/admin/devices -> Updates an existing device.
     */
    @Timed
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_DEVICE, method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDevice(@RequestBody DeviceDTO deviceDTO) throws URISyntaxException {
        log.debug("REST request to update Device : {}", deviceDTO);
        if (deviceDTO.getId() == null) {
            return createDevice(deviceDTO);
        }
        DeviceDTO result = deviceService.save(deviceDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("device", deviceDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  api/admin/devices/:id ->  get the "id" device.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_DEVICE + "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeviceDTO> getDeviceById(@PathVariable Long id) {
        log.debug("REST request to get Device : {}", id);
        DeviceDTO device = deviceService.findOne(id);
        return Optional.ofNullable(device).map(result -> new ResponseEntity<>(result, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * GET  api/admin/devices -> getAll the  devices.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_DEVICE, method = RequestMethod.GET)
    public ResponseEntity<?> getAllDevices(@ApiParam Pageable pageable, PagedResourcesAssembler assembler) {
        log.debug("REST request to get getAllDeviceDTOs ");
        try {

            return new ResponseEntity<>(assembler.toResource(deviceService.findAll(pageable)), HttpStatus.OK);

        } catch (Exception ex) {

            systemErrorService.save(new SystemErrorDTO(ex.getMessage(), ErrorType.GET_ALL_DEVICES, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("deviceService.getAllDevices", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

    /**
     * DELETE  api/admin/devices/:id -> delete the "id" device.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_DEVICE + "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        log.debug("REST request to delete Device : {}", id);
        deviceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("device", id.toString())).build();
    }

    /**
     * GET  api/v1/devices/:id -> get the "id" device.
     */
    @RequestMapping(value = Constants.URL_V1 + Constants.URL_DEVICE + "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeviceDTO> getDevice(@PathVariable Long id) {
        log.debug("REST request to get Device : {}", id);
        DeviceDTO device = deviceService.findOne(id);
        return Optional.ofNullable(device).map(result -> new ResponseEntity<>(result, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}