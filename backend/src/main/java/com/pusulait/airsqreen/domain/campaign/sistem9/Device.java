package com.pusulait.airsqreen.domain.campaign.sistem9;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.config.constants.Sequences;
import com.pusulait.airsqreen.domain.base.AuditBase;
import com.pusulait.airsqreen.domain.integration.PlatformUser;
import com.pusulait.airsqreen.domain.security.user.UserRole;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by benan on 8/2/2017.
 */


@Data
@SQLDelete(sql = "update " + Constants.PREFIX + "DEVICES SET DATA_STATUS = 'DELETED' WHERE id = ? AND version = ? ")
@Where(clause = "DATA_STATUS <> 'DELETED'")
@Entity
@Table(name = Constants.PREFIX + "DEVICES")
public class Device extends AuditBase implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Sequences.DEVICE_SEQUENCE)
    @SequenceGenerator(name = Sequences.DEVICE_SEQUENCE, sequenceName = Sequences.DEVICE_SEQUENCE, allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name =  "NAME")
    private String name;

    @Column(name =  "EXTERNAL_DEVICE_ID")
    private String externalDeviceId;

    @JoinColumn(name = "PLATFORM_USER_ID", insertable = false, updatable = false)
    @ManyToOne
    private PlatformUser platformUser;

    @Column(name = "PLATFORM_USER_ID")
    private Long platformUserId;

    @Column(name = "LATITUDE")
    private BigDecimal latitude;

    @Column(name = "LONGITUDE")
    private BigDecimal longitude;

    @JsonIgnore
    @RestResource(exported = false)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "device")
    private List<DeviceConstraint> deviceConstraintList = new ArrayList<>();




}
