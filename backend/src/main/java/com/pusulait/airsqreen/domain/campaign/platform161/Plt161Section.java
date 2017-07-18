package com.pusulait.airsqreen.domain.campaign.platform161;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.campaign.Section;
import com.pusulait.airsqreen.domain.pg_hibernate.LongArrayType;
import com.pusulait.airsqreen.domain.pg_hibernate.StringArrayType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bhdr on 17.07.2017.
 */
@Entity
@Table(name = Constants.PREFIX + "PLATFORM_161_SECTIONS")
@TypeDefs(value = {
        @TypeDef(name = "text", typeClass = StringArrayType.class)
        , @TypeDef(name = "longarray", typeClass = LongArrayType.class)
        })
@Data
public class Plt161Section extends Section {

    @Column(name = "PUBLISHER_ID")
    private Long publisher_id;// (Number) — The ID of the publisher that owns the section.

    @Column(name = "SITE_ID")
    private Long site_id;// (Number) — The ID of the the site this section belongs to.

    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] size_ids;

    @Column(name = "ADVERTISER_FILTER")
    private String advertiser_filter;

    @Type(type = "longarray")
    @Column(name = "FILTERED_ADVERTISER_IDS", columnDefinition = "text")
    private Long[] filtered_advertiser_ids;

    @Column(name = "CAMPAIGN_FILTER")
    private String campaign_filter;

    @Type(type = "longarray")
    @Column(name = "FILTERED_CAMPAIGN_IDS", columnDefinition = "text")
    private Long[] filtered_campaign_ids;

    @Type(type = "text")
    @Column(name = "EXCLUDED_PRICING_TYPES", columnDefinition = "text")
    private String[] excluded_pricing_types;

    @Column(name = "SHARED")
    private Boolean shared;


}
