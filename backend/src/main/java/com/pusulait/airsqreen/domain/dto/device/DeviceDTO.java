package com.pusulait.airsqreen.domain.dto.device;

import com.pusulait.airsqreen.domain.campaign.sistem9.Device;
import lombok.Data;

@Data
public class DeviceDTO {

    private Long id;
    private String name;
    private String externalDeviceId;
    private Long platformUserId;

    public static DeviceDTO toDTO(Device device) {

        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setExternalDeviceId(device.getExternalDeviceId());
        deviceDTO.setId(device.getId());
        deviceDTO.setName(device.getName());
        deviceDTO.setPlatformUserId(device.getPlatformUserId());
        return deviceDTO;
    }

    public static Device toEntity(DeviceDTO deviceDTO) {

        Device device = new Device();
        device.setExternalDeviceId(deviceDTO.getExternalDeviceId());
        device.setId(deviceDTO.getId());
        device.setName(deviceDTO.getName());
        device.setPlatformUserId(deviceDTO.getPlatformUserId());
        return device;
    }
}
