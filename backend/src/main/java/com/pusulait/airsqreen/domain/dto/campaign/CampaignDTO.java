package com.pusulait.airsqreen.domain.dto.campaign;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;
import com.pusulait.airsqreen.domain.dto.campaign.enums.DeliveryType;
import com.pusulait.airsqreen.domain.dto.campaign.enums.FrequencyCapType;
import com.pusulait.airsqreen.domain.dto.campaign.enums.PricingType;
import com.pusulait.airsqreen.domain.dto.campaign.enums.RtbOptimizeType;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by benan on 7/9/2017.
 */

@Data
public class CampaignDTO {

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
    private String[] rtb_urls;
    private String shared_channel_filter;
    private Long[] shared_channel_ids;
    private Long[] country_ids;
    private Long[] region_ids;
    private Long[] city_ids;
    private String[] postal_codes;
    private String postal_code_filter;
    private Double booked_budget;
    private Double agency_fee;
    private Double platform161_tech_fee;
    private String internal_purchase_order_number;
    private Long[] device_type_ids;
    private Long[] browser_ids;
    private Long[] language_ids;
    private String mobile_app_filter;
    private Long[] mobile_app_ids;
    private String[] supply_types;
    private List<InventorySource> inventory_sources;
    private Long[] operating_system_ids;
    private Long[] direct_deal_ids;

    public static CampaignDTO toDTO(Plt161Campaign campaign) {

        CampaignDTO campaignDTO = new CampaignDTO();
        return campaignDTO;
    }

    public static Plt161Campaign toEntity(CampaignDTO campaignDTO) {

        Plt161Campaign campaign = new Plt161Campaign();
        campaign.setExternalId(campaignDTO.getId());
       // campaign.setDirect_deal_ids(campaignDTO.getDirect_deal_ids());
        campaign.setSupply_types(campaignDTO.getSupply_types());
        return campaign;
    }

}
