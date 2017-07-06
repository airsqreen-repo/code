package com.pusulait.airsqreen.service.security;

import com.pusulait.airsqreen.domain.dto.security.UserRoleDTO;
import com.pusulait.airsqreen.domain.security.user.UserRole;
import com.pusulait.airsqreen.repository.security.user.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Service class for managing userRoles.
 */
@Service
@Transactional
@Slf4j
public class UserRoleService {

    @Inject
    private UserRoleRepository userRoleRepository;

    /**
     * Save a userRole.
     *
     * @return the persisted entity
     */
    public UserRoleDTO save(UserRoleDTO userRoleDTO) {
        log.debug("Request to save UserRole : {}", userRoleDTO);
        UserRole result = userRoleRepository.save(UserRoleDTO.toEntity(userRoleDTO));
        return UserRoleDTO.toDTO(result);
    }

    /**
     * get all the userRoles.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserRoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserRoles");
        return userRoleRepository.findAll(pageable).map(UserRoleDTO::toDTO);

    }
    /**
     * get all the userRoles.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UserRoleDTO> findUserRoles(Long userId) {
        log.debug("Request to get all UserRoles");
        List<UserRole> userRoles = userRoleRepository.findByUserId(userId);
        return UserRoleDTO.toDTO(userRoles);

    }
    /**
     * get one userRole by id.
     *
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserRoleDTO findOne(Long id) {
        log.debug("Request to get UserRole : {}", id);
        UserRoleDTO userRoleDTO = UserRoleDTO.toDTO(userRoleRepository.findOne(id));
        return userRoleDTO;
    }

    /**
     * delete the  userRole by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserRole : {}", id);
        userRoleRepository.delete(id);
    }



}
