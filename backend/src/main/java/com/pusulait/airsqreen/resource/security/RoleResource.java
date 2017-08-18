package com.pusulait.airsqreen.resource.security;

import com.codahale.metrics.annotation.Timed;
import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.base.DataStatus;
import com.pusulait.airsqreen.domain.dto.error.ErrorDTO;
import com.pusulait.airsqreen.domain.dto.error.SystemErrorDTO;
import com.pusulait.airsqreen.domain.dto.security.RightDTO;
import com.pusulait.airsqreen.domain.dto.security.RoleDTO;
import com.pusulait.airsqreen.domain.dto.security.RoleRightDTO;
import com.pusulait.airsqreen.domain.enums.ErrorType;
import com.pusulait.airsqreen.security.SecurityUtils;
import com.pusulait.airsqreen.service.SystemErrorService;
import com.pusulait.airsqreen.service.security.RoleRightService;
import com.pusulait.airsqreen.service.security.RoleService;
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
 * Service class for managing roles.
 */
@RestController
@RequestMapping(value = "/api",produces = "application/hal+json")
@Slf4j
public class RoleResource {

    @Inject
    private RoleService roleService;

    @Inject
    private RoleRightService roleRightService;

    @Autowired
    private SystemErrorService systemErrorService;

    /**
     * POST  api/admin/roles -> Create a new role.
     */
    @Timed
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_ROLE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) throws URISyntaxException {
        log.debug("REST request to save Role : {}", roleDTO);
        if (roleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(Constants.API_URL_ROLE, "idexists",
                    "A new role cannot already have an ID")).body(null);
        }
        RoleDTO result = roleService.save(roleDTO);
        return ResponseEntity.created(new URI(Constants.API_URL_ROLE + (result.getId()!=null?result.getId():-1)))
                .headers(HeaderUtil.createEntityCreationAlert(Constants.API_URL_ROLE, (result.getId()!=null?result.getId().toString():"-1")))
                .body(result);
    }

    /**
     * PATCH  api/admin/roles -> Updates an existing role.
     */
    @Timed
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_ROLE, method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDTO> update(@RequestBody RoleDTO roleDTO) throws URISyntaxException {
        log.debug("REST request to update Role : {}", roleDTO);
        if (roleDTO.getId() == null) {
            return createRole(roleDTO);
        }
        RoleDTO role = roleService.save(roleDTO);
        if(role.getRoleRightList() !=null ){
            role.setRoleRightList(roleRightService.findByRoleId(role.getId()));
        }

        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(Constants.API_URL_ROLE, roleDTO.getId().toString()))
                .body(role);
    }

    /**
     * DELETE  admin/roles/:id -> delete the "id" role.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_ROLE + "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        log.debug("REST request to delete Role : {}", id);
        roleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("role", id.toString())).build();
    }

    /**
     * GET  api/admin/roles/{:id} -> get the role by "id" .
     */
    @Timed
    @ResponseBody
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_ROLE+"/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRole(@PathVariable Long id) {
        log.debug("REST request to get Role : {}", id);
        try{
            RoleDTO role = roleService.findOneWithRights(id);

            return Optional.ofNullable(role)
                    .map(result -> new ResponseEntity<>(
                            result,
                            HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception ex){
            systemErrorService.save(new SystemErrorDTO(ex.toString(),ErrorType.GET_ROLE, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("roleService.getRole", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

    /**
     * GET  api/admin/roles/{:id} -> get the role by "id" .
     */
    @Timed
    @ResponseBody
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_ROLE + "/{id}/rightList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRoleWithRights(@PathVariable Long id) {
        log.debug("REST request to get Role : {}", id);
        try{
            RoleDTO role = roleService.findOneWithRights(id);


            return Optional.ofNullable(role)
                    .map(result -> new ResponseEntity<>(
                            result,
                            HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception ex){
            systemErrorService.save(new SystemErrorDTO(ex.toString(),ErrorType.GET_ROLE, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("roleService.getRoleWithRights", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

    /**
     * GET  api/admin/roles/{:roleId}/roleRights -> get the role by "id" .
     */
    @Timed
    @ResponseBody
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_ROLE+"/{roleId}/" + Constants.URL_ROLE_RIGHT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRoleRights(@PathVariable Long roleId) {
        log.debug("REST request to get RoleRight : {}", roleId);
        try{
            List<RoleRightDTO> roleRightDTO = roleRightService.findByRoleId(roleId);

            return Optional.ofNullable(roleRightDTO)
                    .map(result -> new ResponseEntity<>(
                            result,
                            HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception ex){
            systemErrorService.save(new SystemErrorDTO(ex.toString(),ErrorType.GET_ROLE_RIGHT, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("roleRightService.findByRoleId", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

    /**
     * GET  api/admin/roles -> get all roles.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_ROLE, method = RequestMethod.GET)
    public ResponseEntity<?> getAllRoles(@ApiParam Pageable pageable, PagedResourcesAssembler assembler) {
        log.debug("REST request to get getAllRoles");
        try {
            final Page<RoleDTO> page = roleService.findAll(pageable);
            return new ResponseEntity<>(assembler.toResource(page), HttpStatus.OK);

        } catch (Exception ex) {
            systemErrorService.save(new SystemErrorDTO(ex.toString(),ErrorType.GET_ALL_ROLE, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("roleService.getAllRoles", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

    /**
     * GET  api/admin/roles/search/findAllRoles -> get all findAllRoles Rights.
     */
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_ROLE+ Constants.URL_SEARCH +"/findAllRoles", method = RequestMethod.GET)
    public ResponseEntity<?> findAllRoles(@RequestParam(value="name", required=false) String name, @RequestParam(value="dataStatus", required=false) DataStatus dataStatus, @ApiParam Pageable pageable, PagedResourcesAssembler assembler) {
        log.debug("REST request to get findAllRoles ");
        try {
            final Page<RoleDTO> page = roleService.findAll(name,dataStatus,pageable);
            return new ResponseEntity<>(assembler.toResource(page), HttpStatus.OK);
        } catch (Exception ex) {
            systemErrorService.save(new SystemErrorDTO(ex.toString(),ErrorType.GET_ALL_ROLE, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("roleService.findAllRoles", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

    /**
     * GET  api/admin/rights -> get all right .
     **/
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_RIGHT, method = RequestMethod.GET)
    public ResponseEntity<?> getAllRights(@ApiParam Pageable pageable, PagedResourcesAssembler assembler) {
        log.debug("REST request to get getAllRights");
        try {
            final Page<RightDTO> page = roleService.findAllRights(pageable);
            return new ResponseEntity<>(assembler.toResource(page), HttpStatus.OK);

        } catch (Exception ex) {
            systemErrorService.save(new SystemErrorDTO(ex.toString(),ErrorType.GET_ALL_RIGHT, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("roleService.getAllRights", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }

}
