package com.pusulait.airsqreen.resource.security;

import com.codahale.metrics.annotation.Timed;
import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.dto.ErrorDTO;
import com.pusulait.airsqreen.domain.dto.SystemErrorDTO;
import com.pusulait.airsqreen.domain.dto.security.UserRoleDTO;
import com.pusulait.airsqreen.domain.enums.ErrorType;
import com.pusulait.airsqreen.security.SecurityUtils;
import com.pusulait.airsqreen.service.SystemErrorService;
import com.pusulait.airsqreen.service.security.UserRoleService;
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

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing users.
 */
@RestController
@RequestMapping(value = "/api",produces = "application/hal+json")
@Slf4j
public class UserRoleResource {

    @Inject
    private UserRoleService userRoleService;

    @Autowired
    private SystemErrorService systemErrorService;


    /**
     * POST  /countries -> Create a new user.
     */
    @Timed
    @RequestMapping(value = Constants.URL_ADMIN + Constants.API_URL_USER_ROLE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRoleDTO> createUserRole(@RequestBody UserRoleDTO userRoleDTO) throws URISyntaxException {
        log.debug("REST request to save UserRoleDTO : {}", userRoleDTO);
        if (userRoleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user", "idexists",
                    "A new user cannot already have an ID")).body(null);
        }
        UserRoleDTO result = userRoleService.save(userRoleDTO);
        return ResponseEntity.created(new URI(Constants.API_URL_USER + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("user", result.getId().toString()))
                .body(result);
    }

    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_USER_ROLE, method = RequestMethod.GET)
    public ResponseEntity<?> getAllUserRoles(@ApiParam Pageable pageable, PagedResourcesAssembler assembler) {
        Page<UserRoleDTO> users = userRoleService.findAll(pageable);
        log.debug("REST request to get getAllUserRoles");
        try {
            final Page<UserRoleDTO> page = userRoleService.findAll(pageable);

            return new ResponseEntity<>(assembler.toResource(users), HttpStatus.OK);
        } catch (Exception ex) {

            systemErrorService.save(new SystemErrorDTO(ex.toString(),ErrorType.GET_ALL_USERS, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("userRoleService.getAllUsers", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_USER + "/{id}/userRoleList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserRoles(@PathVariable Long id, PagedResourcesAssembler assembler) {
        log.debug("REST request to get UserRoles : {}", id);
        try{
            List<UserRoleDTO> userRoles = userRoleService.findUserRoles(id);
            return Optional.ofNullable(userRoles)
                    .map(result -> new ResponseEntity<>(
                            result,
                            HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception ex){
            systemErrorService.save(new SystemErrorDTO(ex.toString(),ErrorType.GET_USER_ROLES, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("userRoleService.get", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }
    /**
     * PUT  /users -> Updates an existing user.
     */
    @Timed
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_USER_ROLE, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRoleDTO> updateUserRole(@RequestBody UserRoleDTO userRoleDTO) throws URISyntaxException {
        log.debug("REST request to update UserRole : {}", userRoleDTO);
        if (userRoleDTO.getId() == null) {
           return createUserRole(userRoleDTO);
        }
        UserRoleDTO result = userRoleService.save(userRoleDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("userRole", userRoleDTO.getId().toString()))
                .body(result);
    }

}
