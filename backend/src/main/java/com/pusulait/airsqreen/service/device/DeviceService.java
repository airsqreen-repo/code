package com.pusulait.airsqreen.service.device;

import com.pusulait.airsqreen.domain.base.DataStatus;
import com.pusulait.airsqreen.domain.campaign.sistem9.Device;
import com.pusulait.airsqreen.domain.campaign.sistem9.DeviceConstraint;
import com.pusulait.airsqreen.domain.dto.device.DeviceConstraintDTO;
import com.pusulait.airsqreen.domain.dto.device.DeviceDTO;
import com.pusulait.airsqreen.domain.dto.platform.PlatformUserDTO;
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
        Device device = null;
        if(deviceDTO.getId()!=null){
            device = deviceRepository.findOne(deviceDTO.getId());
        }
        else {
            device = new Device();
        }
        Device result = deviceRepository.save(DeviceDTO.toEntity(deviceDTO, device));
        return DeviceDTO.toDTO(result);
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

    @Transactional(readOnly = true)
    public Page<DeviceDTO> search(String name, DataStatus dataStatus, Pageable pageable) throws  Exception {
        log.debug("Request to get all findByNameAndStatus");
        if(name!=null && dataStatus !=null)
            return deviceRepository.findByNameContainingIgnoreCaseAndDataStatus(name, dataStatus, pageable).map(DeviceDTO::toDTO);
        else if( name!=null)
            return deviceRepository.findByNameContainingIgnoreCase(name, pageable).map(DeviceDTO::toDTO);
        return deviceRepository.findByDataStatus(dataStatus, pageable).map(DeviceDTO::toDTO);
    }


}
