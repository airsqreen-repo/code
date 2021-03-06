package com.pusulait.airsqreen.service.platform;

import com.pusulait.airsqreen.domain.base.DataStatus;
import com.pusulait.airsqreen.domain.campaign.sistem9.DeviceConstraint;
import com.pusulait.airsqreen.domain.dto.device.DeviceConstraintDTO;
import com.pusulait.airsqreen.domain.dto.platform.PlatformUserDTO;
import com.pusulait.airsqreen.domain.integration.PlatformUser;
import com.pusulait.airsqreen.repository.plt161.PlatformUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class PlatformUserService {

    @Autowired
    private PlatformUserRepository platformUserRepository;


    @Transactional
    public PlatformUserDTO save(PlatformUserDTO platformUserDTO) {

        PlatformUser platformUser = null;
        if(platformUserDTO.getId()!=null){
            platformUser = platformUserRepository.findOne(platformUserDTO.getId());
        }
        else {
            platformUser = new PlatformUser();
        }
        PlatformUser result = platformUserRepository.save(PlatformUserDTO.toEntity(platformUserDTO, platformUser));
        return PlatformUserDTO.toDTO(result);
    }

    @Transactional(readOnly = true)
    public Page<PlatformUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlatformUsers");
        return platformUserRepository.findAll(pageable).map(PlatformUserDTO::toDTO);
    }

    @Transactional(readOnly = true)
    public PlatformUserDTO findOne(Long id) {
        log.debug("Request to get PlatformUser : {}", id);
        PlatformUserDTO platformUserDTO = PlatformUserDTO.toDTO(platformUserRepository.findOne(id));
        return platformUserDTO;
    }

    public void delete(Long id) {
        log.debug("Request to delete PlatformUser : {}", id);
        platformUserRepository.delete(id);
    }


    /**
     * get all the platform user  for search
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PlatformUserDTO> findByUsernameAndStatus(String username, DataStatus dataStatus, Pageable pageable) throws  Exception {
        log.debug("Request to get all findByNameAndStatus");
        if(username!=null && dataStatus !=null)
            return platformUserRepository.findByUsernameContainingIgnoreCaseAndDataStatus(username, dataStatus, pageable).map(PlatformUserDTO::toDTO);
        else if(username!=null)
            return platformUserRepository.findByUsernameContainingIgnoreCase(username, pageable).map(PlatformUserDTO::toDTO);
        return platformUserRepository.findByDataStatus(dataStatus, pageable).map(PlatformUserDTO::toDTO);
    }


}

