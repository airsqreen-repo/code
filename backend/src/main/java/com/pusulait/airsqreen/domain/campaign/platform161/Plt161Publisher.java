package com.pusulait.airsqreen.domain.campaign.platform161;

/**
 * Created by bhdr on 17.07.2017.
 */

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.campaign.Publisher;
import com.pusulait.airsqreen.domain.pg_hibernate.LongArrayType;
import com.pusulait.airsqreen.domain.pg_hibernate.StringArrayType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = Constants.PREFIX + "PLATFORM_161_PUBLISHERS")
@TypeDefs(value = {
        @TypeDef(name = "text", typeClass = StringArrayType.class)
        , @TypeDef(name = "longarray", typeClass = LongArrayType.class)
        })
@Data
public class Plt161Publisher extends Publisher {

    @Column(name="FILTER_ANOMALIES")
    private Boolean filter_anomalies;

    @Column
    private Long db_cr_number;

    @Column
    private Long credit_limit;

    @Column
    private String email;

    @Column
    private String address;

    @Column
    private String postal_code;

    @Column
    private String city;

    @Column
    private String region;

    @Column
    private String phone;

    @Column
    private String mobile;

    @Column
    private String fax;

    @Column
    private Long country_id;

    @Column
    private Boolean use_business_address_for_billing;

    @Column
    private String billing_address;

    @Column
    private String billing_postal_code;

    @Column
    private String billing_city;

    @Column
    private String billing_region;

    @Column
    private String billing_phone;

    @Column
    private String billing_mobile;

    @Column
    private String billing_fax;

    @Column
    private String billing_email;

    @Column
    private Long billing_country_id;

    @Column
    private String custom_impression_html;

    @Column
    private String agency_filter;

    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] filtered_agency_ids;

    @Column
    private String advertiser_filter;

    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] filtered_advertiser_ids;

    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] excluded_offer_ids;

    @Type(type = "text")
    @Column(columnDefinition = "text")
    private String[] excluded_pricing_types;

    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] site_ids;

    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] section_ids;


}
