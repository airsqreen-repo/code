package com.pusulait.airsqreen.service.platform;

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
    public void save(PlatformUserDTO platformUserDTO) {
        PlatformUser platformUser = PlatformUserDTO.toEntity(platformUserDTO);
        platformUserRepository.save(platformUser);
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
}

