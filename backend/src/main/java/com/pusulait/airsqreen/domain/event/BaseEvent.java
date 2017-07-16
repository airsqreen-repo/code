package com.pusulait.airsqreen.domain.event;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.config.constants.Sequences;
import com.pusulait.airsqreen.domain.base.AuditBase;
import com.pusulait.airsqreen.domain.enums.EventStatus;
import com.pusulait.airsqreen.domain.enums.EventType;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by benan on 7/16/2017.
 */
@Data
@Entity
@Table(name = Constants.PREFIX + "EVENTS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "EVENT_TYPE", discriminatorType = DiscriminatorType.STRING)
public class BaseEvent extends AuditBase implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Sequences.EVENT_SEQUENCE)
    @SequenceGenerator(name = Sequences.EVENT_SEQUENCE, sequenceName = Sequences.EVENT_SEQUENCE, allocationSize = 1, initialValue = 2)
    private Long id;

    @Column(name = "SLAVE_ID")
    private Long slaveId;

    @Column(name = "EVENT_TYPE",insertable = false,updatable = false)
    private EventType eventType;

    @Column(name = "EVENT_STATUS")
    private EventStatus eventStatus;

    @Column(name = "EXPIRE_DATE")
    private Date expireDate;

}
