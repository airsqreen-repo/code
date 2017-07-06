package com.pusulait.airsqreen.service;

import com.pusulait.airsqreen.domain.SystemError;
import com.pusulait.airsqreen.domain.dto.SystemErrorDTO;
import com.pusulait.airsqreen.repository.error.SystemErrorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 */

@Slf4j
@Service
@Transactional
public class SystemErrorService {

    @Autowired
    private SystemErrorRepository systemErrorRepository;


    @Transactional
    public void save(SystemErrorDTO systemErrorDTO) {

        SystemError systemError = SystemErrorDTO.convertSystemError(systemErrorDTO);
        systemErrorRepository.save(systemError);


    }


}
