package com.pusulait.airsqreen.domain.campaign;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.base.AuditBase;
import com.pusulait.airsqreen.domain.campaign.sistem9.Device;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Created by benan on 8/2/2017.
 */

@Data
@Entity
@Table(name = Constants.PREFIX + "CAMPAIGN_SECTIONS")
@SQLDelete(sql = "update " + Constants.PREFIX + "CAMPAIGN_SECTIONS SET DATA_STATUS = 'DELETED' WHERE id = ? AND version = ?")
@Where(clause = "DATA_STATUS <> 'DELETED'")
@ToString(exclude = {"campaign","section"})
@EqualsAndHashCode(exclude = {"campaign","section"})
public class CampaignSection extends AuditBase {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CAMPAIGN_SECTION_ID")
    @SequenceGenerator(name = "SEQ_CAMPAIGN_SECTION_ID", sequenceName = "SEQ_CAMPAIGN_SECTION_ID", allocationSize = 1,initialValue =2)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CAMPAIGN_ID", nullable = false, insertable = false, updatable = false)
    private Campaign campaign;

    @Column(name = "CAMPAIGN_ID")
    private Long campaignId;

    @ManyToOne
    @JoinColumn(name = "SECTION_ID", nullable = false, insertable = false, updatable = false)
    private Section section;

    @Column(name = "SECTION_ID")
    private Long sectionId;


    @ManyToOne
    @JoinColumn(name = "DEVICE_ID",  insertable = false, updatable = false)
    private Device device;

    @Column(name = "DEVICE_ID")
    private Long deviceId;

    @Column(name = "ACTION_ID")
    private String actionId;

    @Column(name = "KEY")
    private String key;

    @Column(name = "SSP_PRICE")
    private Double sspPrice;

}


