package com.pusulait.airsqreen.domain.campaign;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.config.constants.Sequences;
import com.pusulait.airsqreen.domain.base.AuditBase;
import com.pusulait.airsqreen.domain.campaign.sistem9.Device;
import com.pusulait.airsqreen.domain.enums.CampaignConstraintFilter;
import com.pusulait.airsqreen.domain.enums.CampaignConstraintType;
import com.pusulait.airsqreen.domain.enums.DeviceConstraintFilter;
import com.pusulait.airsqreen.domain.enums.DeviceConstraintType;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by benan on 8/2/2017.
 */

@Data
@SQLDelete(sql = "update " + Constants.PREFIX + "CAMPAIGN_CONSTRAINTS SET DATA_STATUS = 'DELETED' WHERE id = ? AND version = ? ")
@Where(clause = "DATA_STATUS <> 'DELETED'")
@Entity
@Table(name = Constants.PREFIX + "CAMPAIGN_CONSTRAINTS")
public class CampaignConstraint extends AuditBase implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Sequences.CAMPAIGN_CONSTRAINT_SEQUENCE)
    @SequenceGenerator(name = Sequences.CAMPAIGN_CONSTRAINT_SEQUENCE, sequenceName = Sequences.CAMPAIGN_CONSTRAINT_SEQUENCE, allocationSize = 1, initialValue = 1)
    private Long id;

    @JoinColumn(name = "CAMPAIGN_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne
    private Campaign campaign;

    @Column(name = "CAMPAIGN_ID")
    private Long campaignId;

    @Enumerated(EnumType.STRING)
    @Column(name = "CAMPAIGN_CONSTRAINT_TYPE")
    private CampaignConstraintType campaignConstraintType;

    @Enumerated(EnumType.STRING)
    @Column(name = "CAMPAIGN_CONSTRAINT_FILTER")
    private CampaignConstraintFilter campaignConstraintFilter;

    @Column(columnDefinition = "text")
    private String filter_detail;

}
