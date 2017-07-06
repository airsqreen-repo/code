package com.pusulait.airsqreen.service.security;

import com.pusulait.airsqreen.domain.dto.security.RoleRightDTO;
import com.pusulait.airsqreen.domain.security.user.RoleRight;
import com.pusulait.airsqreen.repository.security.user.RoleRightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Service class for managing roleRights.
 */
@Service
@Transactional
@Slf4j
public class RoleRightService {

    @Inject
    private RoleRightRepository roleRightRepository;

    /**
     * Save a roleRight.
     *
     * @return the persisted entity
     */
    public RoleRightDTO save(RoleRightDTO roleRightDTO) {
        log.debug("Request to save RoleRight : {}", roleRightDTO);
        RoleRight result = roleRightRepository.save(RoleRightDTO.toEntity(roleRightDTO));
        roleRightRepository.save(result);
        return RoleRightDTO.toDTO(result);
    }

    /**
     * get all the roleRights.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RoleRightDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RoleRights");
        return roleRightRepository.findAll(pageable).map(RoleRightDTO::toDTO);

    }

    /**
     * get all the Role of roleRights.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RoleRightDTO> findByRoleId(Long roleId) {
        log.debug("Request to get all RoleRights");
        return RoleRightDTO.toDTO(roleRightRepository.findByRoleId(roleId));

    }

    /**
     * get one roleRight by id.
     *
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RoleRightDTO findOne(Long id) {
        log.debug("Request to get RoleRight : {}", id);
        RoleRightDTO roleRightDTO = RoleRightDTO.toDTO(roleRightRepository.findOne(id));
        return roleRightDTO;
    }

    /**
     * delete the  roleRight by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete RoleRight : {}", id);
        roleRightRepository.delete(id);
    }



}
