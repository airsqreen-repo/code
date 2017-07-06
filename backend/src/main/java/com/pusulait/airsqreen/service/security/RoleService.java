package com.pusulait.airsqreen.service.security;

import com.pusulait.airsqreen.domain.base.DataStatus;
import com.pusulait.airsqreen.domain.dto.security.RightDTO;
import com.pusulait.airsqreen.domain.dto.security.RoleDTO;
import com.pusulait.airsqreen.domain.dto.security.RoleRightDTO;
import com.pusulait.airsqreen.domain.security.user.Role;
import com.pusulait.airsqreen.domain.security.user.RoleRight;
import com.pusulait.airsqreen.repository.security.user.RightRepository;
import com.pusulait.airsqreen.repository.security.user.RoleRepository;
import com.pusulait.airsqreen.repository.security.user.RoleRightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing roles.
 */
@Service
@Transactional
@Slf4j
public class RoleService {

    @Inject
    private RoleRepository roleRepository;

    @Inject
    private RightRepository rightRepository;

    @Inject
    private RoleRightRepository roleRightRepository;
    /**
     * Save a role.
     *
     * @return the persisted entity
     */
    public RoleDTO save(RoleDTO roleDTO) {
        log.debug("Request to save Role : {}", roleDTO);
        Role roleEntity = null;
        if(roleDTO.getId()!=null){
            roleEntity = roleRepository.findOne(roleDTO.getId());
        }
        else {
            roleEntity = new Role();
        }

        Role result = roleRepository.save(RoleDTO.toEntity(roleDTO,roleEntity));

        List<RoleRight> roleRights= roleRightRepository.findByRoleId(roleDTO.getId());
        List<RoleRightDTO> roleRightList = roleDTO.getRoleRightList();

        if (roleRightList!=null && !roleRightList.isEmpty()) {
            for (RoleRightDTO item : roleRightList) {
                List<RoleRight>  anyRoleRights = roleRights.stream()
                        .filter(p -> p.getRightId() == item.getRightId() && p.getRoleId()==item.getRoleId())
                        .collect(Collectors.toList());
                if (anyRoleRights == null || anyRoleRights.isEmpty()) {
                    roleRightRepository.save(RoleRightDTO.toEntity(item,new RoleRight()));
                }
            }
            for (RoleRight item : roleRights) {
                List<RoleRightDTO>  anyRoles = roleRightList.stream()
                        .filter(p -> p.getRightId() == item.getRightId() && p.getRoleId()==item.getRoleId())
                        .collect(Collectors.toList());
                if (anyRoles==null || anyRoles.isEmpty()) {
                    roleRightRepository.delete(item.getId());
                }
            }
        } else if (roleRights !=null && !roleRights.isEmpty()) {
            for (RoleRight item : roleRights) {
                roleRightRepository.delete(item.getId());
            }
        }
        return RoleDTO.toDTOWithRights(result);
    }

    /**
     * get all the roles.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Roles");
        return roleRepository.findAll(pageable).map(RoleDTO::toDTO);

    }

    /**
     * get one role by id.
     *
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RoleDTO findOne(Long id) {
        log.debug("Request to get Role : {}", id);
        RoleDTO roleDTO = RoleDTO.toDTO(roleRepository.findOne(id));
        return roleDTO;
    }

    /**
     * get one user by id.
     *
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RoleDTO findOneWithRights(Long id) {
        log.debug("Request to get User : {}", id);
        RoleDTO userDTO = RoleDTO.toDTOWithRights(roleRepository.findOne(id));
        return userDTO;
    }

    /**
     * delete the  role by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Role : {}", id);
        roleRepository.delete(id);
    }


     /** Rights Metods**/

    /**
     * get all the Right.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RightDTO> findAllRights(Pageable pageable) {
        log.debug("Request to get all Rights");
        return rightRepository.findAll(pageable).map(RightDTO::toDTO);

    }
    /**
     * get all the Roles.
     *
     * @return the list of RoleDTOs
     */
    @Transactional(readOnly = true)
    public Page<RoleDTO> findAll(String name, DataStatus dataStatus, Pageable pageable) throws  Exception{
        log.debug("Request to get all RoleDTO");
        if(name!=null && dataStatus !=null)
            return roleRepository.findByNameContainingIgnoreCaseAndDataStatus(name, dataStatus, pageable).map(RoleDTO::toDTO);
        else if(name!=null)
            return roleRepository.findByNameContainingIgnoreCase(name, pageable).map(RoleDTO::toDTO);
        dataStatus = dataStatus !=null ? dataStatus: DataStatus.ACTIVE;
        return roleRepository.findByDataStatus(dataStatus, pageable).map(RoleDTO::toDTO);
    }
    /**
     * get one Right by id.
     *
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RightDTO findOneRight(Long id) {
        log.debug("Request to get Role : {}", id);
        RightDTO rightDTO = RightDTO.toDTO(rightRepository.findOne(id));
        return rightDTO;
    }



}
