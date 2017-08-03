package com.pusulait.airsqreen.domain.dto.event;

import com.pusulait.airsqreen.domain.event.Sistem9PushEvent;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by benan on 7/16/2017.
 */

@Data
public class Sistem9PushEventDTO extends BaseEventDTO {

    private String actionId;
    private Long deviceId;
    private Long platformUserId;
    private Long campaignSectionId;


    public static Sistem9PushEvent toEntity(Sistem9PushEventDTO dto){

        Sistem9PushEvent entity = new Sistem9PushEvent();
        entity.setDeviceId(dto.getDeviceId());
        entity.setPlatformUserId(dto.getPlatformUserId());
        entity.setActionId(dto.getActionId());
        entity.setEventStatus(dto.getEventStatus());
        entity.setExpireDate(dto.getExpireDate());
        entity.setEventType(dto.getEventType());
        entity.setSlaveId(dto.getSlaveId());
        entity.setId(dto.getId());

        return entity;
    }

    public static Sistem9PushEventDTO toDTO(Sistem9PushEvent entity){

        Sistem9PushEventDTO dto = new Sistem9PushEventDTO();
        dto.setActionId(entity.getActionId());
        dto.setDeviceId(entity.getDeviceId());
        dto.setPlatformUserId(entity.getPlatformUserId());
        dto.setEventStatus(entity.getEventStatus());
        dto.setExpireDate(entity.getExpireDate());
        dto.setEventType(entity.getEventType());
        dto.setSlaveId(entity.getSlaveId());
        dto.setId(entity.getId());

        return dto;
    }

}
