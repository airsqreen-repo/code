package com.pusulait.airsqreen.domain.campaign.sistem9;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.config.constants.Sequences;
import com.pusulait.airsqreen.domain.base.AuditBase;
import com.pusulait.airsqreen.domain.enums.DeviceConstraintFilter;
import com.pusulait.airsqreen.domain.enums.DeviceConstraintType;
import com.pusulait.airsqreen.domain.pg_hibernate.StringArrayType;
import lombok.Data;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by benan on 8/2/2017.
 */

@Data
@SQLDelete(sql = "update " + Constants.PREFIX + "DEVICE_CONSTRAINTS SET DATA_STATUS = 'DELETED' WHERE id = ? AND version = ? ")
@Where(clause = "DATA_STATUS <> 'DELETED'")
@Entity
@Table(name = Constants.PREFIX + "DEVICE_CONSTRAINTS")
public class DeviceConstraint extends AuditBase implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Sequences.DEVICE_CONSTRAINT_SEQUENCE)
    @SequenceGenerator(name = Sequences.DEVICE_CONSTRAINT_SEQUENCE, sequenceName = Sequences.DEVICE_CONSTRAINT_SEQUENCE, allocationSize = 1, initialValue = 1)
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name = "DEVICE_CONSTRAINT_TYPE")
    private DeviceConstraintType deviceConstraintType;


    @Enumerated(EnumType.STRING)
    @Column(name = "DEVICE_CONSTRAINT_FILTER")
    private DeviceConstraintFilter deviceConstraintFilter;

    @JoinColumn(name = "DEVICE_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne
    private Device device;

    @Column(name = "DEVICE_ID")
    private Long deviceId;

    @Column(columnDefinition = "text")
    private String filter_detail;

}
