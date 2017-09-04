package com.pusulait.airsqreen.domain.dto.device;

import com.pusulait.airsqreen.domain.base.DataStatus;
import com.pusulait.airsqreen.domain.campaign.sistem9.Device;
import com.pusulait.airsqreen.domain.campaign.sistem9.DeviceConstraint;
import com.pusulait.airsqreen.domain.dto.platform.PlatformUserDTO;
import com.pusulait.airsqreen.domain.enums.DeviceConstraintFilter;
import com.pusulait.airsqreen.domain.enums.DeviceConstraintType;
import lombok.Data;

@Data
public class DeviceConstraintDTO {

    private Long id;
    private DeviceConstraintType deviceConstraintType;
    private DeviceConstraintFilter deviceConstraintFilter;
    private String filter_detail;
    private Long deviceId;
    private DataStatus dataStatus;


    public static DeviceConstraintDTO toDTO(DeviceConstraint deviceConstraint) {

        DeviceConstraintDTO deviceConstraintDTO = new DeviceConstraintDTO();
        deviceConstraintDTO.setId(deviceConstraint.getId());
        deviceConstraintDTO.setDeviceConstraintType(deviceConstraint.getDeviceConstraintType());
        deviceConstraintDTO.setDeviceConstraintFilter(deviceConstraint.getDeviceConstraintFilter());
        deviceConstraintDTO.setFilter_detail(deviceConstraint.getFilter_detail());
        deviceConstraintDTO.setDeviceId(deviceConstraint.getDeviceId());
        deviceConstraintDTO.setDataStatus(deviceConstraint.getDataStatus());
        return deviceConstraintDTO;
    }

    public static DeviceConstraint toEntity(DeviceConstraintDTO deviceConstraintDTO, DeviceConstraint deviceConstraint) {

        deviceConstraint.setId(deviceConstraintDTO.getId());
        deviceConstraint.setDeviceConstraintType(deviceConstraintDTO.getDeviceConstraintType());
        deviceConstraint.setDeviceConstraintFilter(deviceConstraintDTO.getDeviceConstraintFilter());
        deviceConstraint.setFilter_detail(deviceConstraintDTO.getFilter_detail());
        deviceConstraint.setDeviceId(deviceConstraintDTO.getDeviceId());
        deviceConstraint.setDataStatus(deviceConstraintDTO.getDataStatus());
        return deviceConstraint;
    }
}
