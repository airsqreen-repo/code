package com.pusulait.airsqreen.service.device;

import com.pusulait.airsqreen.domain.campaign.sistem9.Device;
import com.pusulait.airsqreen.domain.dto.device.DeviceDTO;
import com.pusulait.airsqreen.repository.device.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Transactional
    public DeviceDTO save(DeviceDTO deviceDTO) {
        Device device = DeviceDTO.toEntity(deviceDTO);
        deviceRepository.save(device);
        return DeviceDTO.toDTO(device);
    }

    @Transactional(readOnly = true)
    public Page<DeviceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Devices");
        return deviceRepository.findAll(pageable).map(DeviceDTO::toDTO);
    }

    @Transactional(readOnly = true)
    public DeviceDTO findOne(Long id) {
        log.debug("Request to get Device : {}", id);
        DeviceDTO deviceDTO = DeviceDTO.toDTO(deviceRepository.findOne(id));
        return deviceDTO;
    }

    public void delete(Long id) {
        log.debug("Request to delete Device : {}", id);
        deviceRepository.delete(id);
    }
}
