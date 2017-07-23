package com.pusulait.airsqreen.domain.event;

import com.pusulait.airsqreen.config.constants.Constants;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by benan on 7/16/2017.
 */

@Data
@Entity
@Table(name = Constants.PREFIX + "SISTEM9_PUSH_EVENTS")
public class Sistem9PushEvent extends BaseEvent {

    @Column(name = "ACTION_ID")
    private String actionId;

    @Column(name = "DEVICE_ID")
    private Integer deviceId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;


}
