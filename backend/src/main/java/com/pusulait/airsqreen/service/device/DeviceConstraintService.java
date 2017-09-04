package com.pusulait.airsqreen.service.device;

import com.pusulait.airsqreen.domain.campaign.sistem9.DeviceConstraint;
import com.pusulait.airsqreen.domain.dto.device.DeviceConstraintDTO;
import com.pusulait.airsqreen.repository.device.DeviceConstraintRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class DeviceConstraintService {

    @Autowired
    private DeviceConstraintRepository deviceConstraintRepository;

    @Transactional
    public DeviceConstraintDTO save(DeviceConstraintDTO deviceConstraintDTO) {
        DeviceConstraint globalCurrencyEntity = null;
        if(deviceConstraintDTO.getId()!=null){
            globalCurrencyEntity = deviceConstraintRepository.findOne(deviceConstraintDTO.getId());
        }
        else {
            globalCurrencyEntity = new DeviceConstraint();
        }
        DeviceConstraint result = deviceConstraintRepository.save(DeviceConstraintDTO.toEntity(deviceConstraintDTO, globalCurrencyEntity));
        return DeviceConstraintDTO.toDTO(result);

    }

    @Transactional(readOnly = true)
    public Page<DeviceConstraintDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Devices");
        return deviceConstraintRepository.findAll(pageable).map(DeviceConstraintDTO::toDTO);
    }

    @Transactional(readOnly = true)
    public DeviceConstraintDTO findOne(Long id) {
        log.debug("Request to get Device : {}", id);
        DeviceConstraintDTO deviceConstraintDTO = DeviceConstraintDTO.toDTO(deviceConstraintRepository.findOne(id));
        return deviceConstraintDTO;
    }

    public void delete(Long id) {
        log.debug("Request to delete Device : {}", id);
        deviceConstraintRepository.delete(id);
    }

    /**
     * get all the deviceDeviceConstraints.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DeviceConstraintDTO> findDeviceDeviceConstraints(Long deviceId) {
        log.debug("Request to get all UserRoles");
       return deviceConstraintRepository.findByDeviceId(deviceId).stream().map(DeviceConstraintDTO::toDTO).collect(Collectors.toList());
    }

}
