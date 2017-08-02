package com.pusulait.airsqreen.domain.campaign.sistem9;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.config.constants.Sequences;
import com.pusulait.airsqreen.domain.base.AuditBase;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

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

}
