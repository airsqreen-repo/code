package com.pusulait.airsqreen.domain.view;

import com.pusulait.airsqreen.domain.enums.EventStatus;
import com.pusulait.airsqreen.domain.enums.EventType;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 */
@Data
@Entity
@Table(name = "EVENT_RUN_REPORT")
@Immutable
public class EventRunReport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    Long id;

    @Column(name = "EVENT_STATUS")
    private EventStatus eventStatus;

    @Column(name = "EXPIRE_DATE")
    private Date expireDate;

    @Column(name = "RUN_DATE")
    private Date runDate;

    @Column(name = "CAMPAIGN_ID")
    private Long campaignId;

    @Column(name = "CAMPAIGN_SECTION_ID")
    private Long campaignSectionId;

    @Column(name = "EVENT_TYPE")
    private EventType eventType;

    @Column(name = "SSP_PRICE")
    private Double sspPrice;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "campaign_name")
    private String campaignName;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "device_id")
    private Long deviceId;

}
