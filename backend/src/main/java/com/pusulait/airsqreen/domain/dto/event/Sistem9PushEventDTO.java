package com.pusulait.airsqreen.domain.dto.event;

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

    private Integer deviceId;

    private String username;

    private String password;


}
