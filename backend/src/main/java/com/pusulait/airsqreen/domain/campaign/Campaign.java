package com.pusulait.airsqreen.domain.campaign;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.config.constants.Sequences;
import com.pusulait.airsqreen.domain.base.AuditBase;
import com.pusulait.airsqreen.domain.dto.campaign.enums.PricingType;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by benan on 7/13/2017.
 */

@Data
@SQLDelete(sql = "update " + Constants.PREFIX + "CAMPAIGNS SET DATA_STATUS = 'DELETED' WHERE id = ? AND version = ? ")
@Where(clause = "DATA_STATUS <> 'DELETED'")
@Entity
@Table(name = Constants.PREFIX + "CAMPAIGNS")
@Inheritance(strategy = InheritanceType.JOINED)
public class Campaign extends AuditBase implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Sequences.CAMPAIGN_SEQUENCE)
    @SequenceGenerator(name = Sequences.CAMPAIGN_SEQUENCE, sequenceName = Sequences.CAMPAIGN_SEQUENCE, allocationSize = 1, initialValue = 2)
    private Long id;

    @Column(name = "EXTERNAL_ID")
    private Long externalId;

    @Column(name = "NAME")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRICING_TYPE")
    private PricingType pricingType;

    @Column(name = "OFFER_ID")
    private Long offerId;

    @Column(name = "TARGET")
    private String target;

    @Column(name = "START_ON")
    private Date startOn;

    @Column(name = "END_ON")
    private Date endOn;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "AGENCY_FEE")
    private Double agencyFee;


}
