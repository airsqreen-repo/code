package com.pusulait.airsqreen.domain.dto.event;

import com.pusulait.airsqreen.domain.base.AuditBase;
import com.pusulait.airsqreen.domain.enums.EventStatus;
import com.pusulait.airsqreen.domain.enums.EventType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by benan on 7/16/2017.
 */
@Data
public class BaseEventDTO extends AuditBase implements Serializable {

    private Long id;
    private Long slaveId;
    private EventType eventType;
    private EventStatus eventStatus;
    private Date expireDate;

}
