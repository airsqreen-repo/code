package com.pusulait.airsqreen.domain.campaign;

import com.pusulait.airsqreen.domain.campaign.enums.DeliveryType;
import com.pusulait.airsqreen.domain.campaign.enums.FrequencyCapType;
import com.pusulait.airsqreen.domain.campaign.enums.PricingType;
import com.pusulait.airsqreen.domain.campaign.enums.RtbOptimizeType;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by benan on 7/9/2017.
 */

@Data
public class Campaign {

    private Long id;
    //private Long externalId;
    private PricingType pricingType;
    private Double price;
    private Boolean active;
    private Boolean archived;
    private Date start_on;
    private Date end_on;
    private String name;
    private Long sales_manager_id;
    private Long campaign_manager_id;
    private Long offer_id;
    private Long media_plan_id;
    private Boolean must_deliver;
    private Long advertiser_id;
    private Date pace_end_on;
    private Double media_budget;
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
    private String targeting_logic;
    private List<String> rtb_urls;
    private String shared_channel_filter;
    private List<Long> shared_channel_ids;
    private List<Long> country_ids;
    private List<Long> region_ids;
    private List<Long> city_ids;
    private List<String> postal_codes;
    private String postal_code_filter;
    private Double booked_budget;
    private Double agency_fee;
    private Double platform161_tech_fee;
    private String internal_purchase_order_number;
    private List<Long> device_type_ids;
    private List<Long> browser_ids;
    private List<Long> language_ids;
    private String mobile_app_filter;
    private List<Long> mobile_app_ids;
    private List<String> supply_types;
    private List<InventorySource> inventory_sources;
    private List<Long> operating_system_ids;
    private List<Long> direct_deal_ids;

}
