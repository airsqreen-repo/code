package com.pusulait.airsqreen.domain.view;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

/**
 */
@Data
@Entity
@Table(name = "ssp_view_count")
@Immutable
public class SspViewCount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private String id;

    @Column(name = "SSP_PRICE")
    private String sspPrice;

    @Column(name = "DEVICE_ID")
    private String deviceId;

    @Column(name = "ACTION_ID")
    private String actionId;

    @Column(name = "SSP_DEVICE_ID")
    private String sspDeviceId;

    @Column(name = "PLATFORM_USER_ID")
    private String platformUserId;

    @Column(name = "DEVICE_NAME")
    private String deviceName;
}
