package com.pusulait.airsqreen.domain.dto.device;

import com.pusulait.airsqreen.domain.base.DataStatus;
import com.pusulait.airsqreen.domain.campaign.sistem9.Device;
import com.pusulait.airsqreen.domain.dto.platform.PlatformUserDTO;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
public class DeviceDTO {

    private Long id;
    private String name;
    private String externalDeviceId;
    private Long platformUserId;
    private PlatformUserDTO platformUser;
    private DataStatus dataStatus;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public static DeviceDTO toDTO(Device device) {

        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setExternalDeviceId(device.getExternalDeviceId());
        deviceDTO.setId(device.getId());
        deviceDTO.setName(device.getName());
        deviceDTO.setPlatformUserId(device.getPlatformUserId());
        if(device.getPlatformUser()!=null)
            deviceDTO.setPlatformUser(PlatformUserDTO.toDTO(device.getPlatformUser()));
        deviceDTO.setDataStatus(device.getDataStatus());
        deviceDTO.setLatitude(device.getLatitude());
        deviceDTO.setLongitude(device.getLongitude());
        return deviceDTO;
    }

    public static Device toEntity(DeviceDTO deviceDTO, Device device) {
        device.setExternalDeviceId(deviceDTO.getExternalDeviceId());
        device.setId(deviceDTO.getId());
        device.setName(deviceDTO.getName());
        device.setPlatformUserId(deviceDTO.getPlatformUserId());
        device.setDataStatus(deviceDTO.getDataStatus());
        device.setLatitude(deviceDTO.getLatitude());
        device.setLongitude(deviceDTO.getLongitude());
        return device;
    }
}
