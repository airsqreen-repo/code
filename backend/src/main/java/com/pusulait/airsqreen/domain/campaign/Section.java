package com.pusulait.airsqreen.domain.campaign;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.config.constants.Sequences;
import com.pusulait.airsqreen.domain.base.AuditBase;
import com.pusulait.airsqreen.domain.dto.campaign.enums.PricingType;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by bhdr on 16.07.2017.
 */
@Data
@SQLDelete(sql = "update " + Constants.PREFIX + "SECTIONS SET DATA_STATUS = 'DELETED' WHERE id = ? AND version = ? ")
@Where(clause = "DATA_STATUS <> 'DELETED'")
@Entity
@Table(name = Constants.PREFIX + "SECTIONS")
@Inheritance(strategy = InheritanceType.JOINED)
public class Section extends AuditBase implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Sequences.SECTION_SEQUENCE)
    @SequenceGenerator(name = Sequences.SECTION_SEQUENCE, sequenceName = Sequences.SECTION_SEQUENCE, allocationSize = 1, initialValue = 2)
    private Long id;

    @Column(name = "NAME")
    private String name; //(String) — The name of section.

    @Column(name = "PRICE")
    private Double price;// (Number) — The price of section.

    @Column(name = "PRICING_TYPE")
    private PricingType pricingType;

    @Column(name = "PUBLISHER_ID")
    private Long publisher_id;// (Number) — The ID of the publisher that owns the section.

    @Column(name = "SITE_ID")
    private Long site_id;// (Number) — The ID of the the site this section belongs to.

    @Type(type = "text")
    @Column(name = "SIZE_IDS", columnDefinition = "text")
    private Long[] size_ids;

    @Column(name = "ADVERTISER_FILTER")
    private String advertiser_filter;

    @Type(type = "text")
    @Column(name = "FILTERED_ADVERTISER_IDS", columnDefinition = "text")
    private Long[] filtered_advertiser_ids;

    @Column(name = "CAMPAIGN_FILTER")
    private String campaign_filter;

    @Type(type = "text")
    @Column(name = "FILTERED_CAMPAIGN_IDS", columnDefinition = "text")
    private Long[] filtered_campaign_ids;

//    @Type(type = "text")
//    @Column(name = "EXCLUDED_PRICING_TYPES", columnDefinition = "text")
//    private String[] excluded_pricing_types;

    @Column(name = "SHARED")
    private Boolean shared;


}
