package com.pusulait.airsqreen.domain.campaign.platform161;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.dto.campaign.enums.DeliveryType;
import com.pusulait.airsqreen.domain.dto.campaign.enums.FrequencyCapType;
import com.pusulait.airsqreen.domain.dto.campaign.enums.RtbOptimizeType;
import com.pusulait.airsqreen.domain.pg_hibernate.LongArrayType;
import com.pusulait.airsqreen.domain.pg_hibernate.StringArrayType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

//import com.pusulait.airsqreen.domain.pg_hibernate.LongArrayType;

/**
 * Created by benan on 7/14/2017.
 */
@Entity
@Table(name = Constants.PREFIX + "PLATFORM_161_CAMPAIGNS")
@TypeDefs(value = {
        @TypeDef(name = "text", typeClass = StringArrayType.class)
       , @TypeDef(name = "longarray", typeClass = LongArrayType.class)
})
@Data
public class Plt161Campaign extends Campaign {

  private Double media_budget;

    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] targeting_weekday_ids;

    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] targeting_hour_ids;

    private Date updated_at;

    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] filtered_section_ids;

    /*  private Boolean archived;
    private Long sales_manager_id;
    private Long campaign_manager_id;
    private Long media_plan_id;
    private Boolean must_deliver;
    private Long advertiser_id;
    private Date pace_end_on;
    private Boolean unlimited_budget;
    private Long contact_id;
    private String daily_cap;
    private String pacing;
    private DeliveryType deliveryType;
    private String agency;
    private Boolean managed;
    private RtbOptimizeType rtbOptimizeType;
    private Double minimum_ctr_percentage;
    private Long rtb_optimize_conversion_id;
    private Long frequency_cap;
    private FrequencyCapType frequencyCapType;
    private Long frequency_cap_count;
    private String shared_channel_filter;
    private String postal_code_filter;
    private Double booked_budget;
    private Double platform161_tech_fee;
    private String mobile_app_filter;*/

 /*   @Column(name = "OFFER_ID")
    private Long offerId;

    @Type(type = "text")
    @Column(columnDefinition = "text")
    private String[] postal_codes;

    private String internal_purchase_order_number;

    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] operating_system_ids;

    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] direct_deal_ids;

    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] device_type_ids;

    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] browser_ids;

    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] language_ids;

    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] mobile_app_ids;

    /* @Column
     private String supply_types;
 */

 /*   @Type(type = "text")
    @Column(columnDefinition = "text")
    private String[] supply_types;


    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] shared_channel_ids;

    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] country_ids;

    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] region_ids;

    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] city_ids;

    @Type(type = "text")
    @Column(columnDefinition = "text")
    private String[] rtb_urls;
*/

    //private List<InventorySource> inventory_sources;

}
