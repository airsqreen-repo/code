package com.pusulait.airsqreen.domain.dto.platform;

import com.pusulait.airsqreen.domain.base.DataStatus;
import com.pusulait.airsqreen.domain.enums.PlatformType;
import com.pusulait.airsqreen.domain.enums.ServiceType;
import com.pusulait.airsqreen.domain.integration.PlatformUser;
import lombok.Data;

import java.io.Serializable;

@Data
public class PlatformUserDTO implements Serializable {

    private Long id;
    private String username;
    private String password;
    private String secret;
    private PlatformType platformType;
    private ServiceType serviceType;
    private DataStatus dataStatus;


    public static PlatformUserDTO toDTO(PlatformUser platformUser) {

        PlatformUserDTO platformUserDTO = new PlatformUserDTO();
        platformUserDTO.setPassword(platformUser.getPassword());
        platformUserDTO.setId(platformUser.getId());
        platformUserDTO.setPlatformType(platformUser.getPlatformType());
        platformUserDTO.setUsername(platformUser.getUsername());
        platformUserDTO.setSecret(platformUser.getSecret());
        platformUserDTO.setServiceType(platformUser.getServiceType());
        platformUserDTO.setDataStatus(platformUser.getDataStatus());
        return platformUserDTO;
    }

    public static PlatformUser toEntity(PlatformUserDTO platformUserDTO, PlatformUser platformUser) {
        platformUser.setPassword(platformUserDTO.getPassword());
        platformUser.setId(platformUserDTO.getId());
        platformUser.setPlatformType(platformUserDTO.getPlatformType());
        platformUser.setUsername(platformUserDTO.getUsername());
        platformUser.setSecret(platformUserDTO.getSecret());
        platformUser.setServiceType(platformUserDTO.getServiceType());
        platformUser.setDataStatus(platformUserDTO.getDataStatus());
        return platformUser;
    }

}
