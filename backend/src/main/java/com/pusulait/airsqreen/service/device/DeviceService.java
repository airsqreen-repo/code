package com.pusulait.airsqreen.service.device;

import com.pusulait.airsqreen.domain.campaign.sistem9.Device;
import com.pusulait.airsqreen.domain.dto.device.DeviceDTO;
import com.pusulait.airsqreen.repository.device.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;


    @Transactional
    public void save(DeviceDTO deviceDTO) {

        Device device = DeviceDTO.toEntity(deviceDTO);
        deviceRepository.save(device);


    }
}
