package com.pusulait.airsqreen.domain.campaign;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.config.constants.Sequences;
import com.pusulait.airsqreen.domain.base.AuditBase;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by bhdr on 17.07.2017.
 */
@Data
@SQLDelete(sql = "update " + Constants.PREFIX + "PUBLISHERS SET DATA_STATUS = 'DELETED' WHERE id = ? AND version = ? ")
@Where(clause = "DATA_STATUS <> 'DELETED'")
@Entity
@Table(name = Constants.PREFIX + "PUBLISHERS")
@Inheritance(strategy = InheritanceType.JOINED)
public class Publisher extends AuditBase implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Sequences.PUBLISHER_SEQUENCE)
    @SequenceGenerator(name = Sequences.PUBLISHER_SEQUENCE, sequenceName = Sequences.PUBLISHER_SEQUENCE, allocationSize = 1, initialValue = 2)
    private Long id;

    @Column(name = "EXTERNAL_ID")
    private Long externalId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CODE")
    private String code;



}
