package com.pusulait.airsqreen.domain.dto.campaign;

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
    private List<InventorySourceDTO> inventory_sources;
    private Long[] operating_system_ids;
    private Long[] direct_deal_ids;

    public static CampaignDTO toDTO(Plt161Campaign campaign) {

        CampaignDTO campaignDTO = new CampaignDTO();
        return campaignDTO;
    }

    public static Plt161Campaign toEntity(CampaignDTO campaignDTO) {

    Plt161Campaign campaign = new Plt161Campaign();
        campaign.setExternalId(campaignDTO.getId());
        campaign.setPricingType(campaignDTO.getPricingType());
        campaign.setActive(campaignDTO.getActive());
        campaign.setAdvertiser_id(campaignDTO.getAdvertiser_id());
        campaign.setAgency(campaignDTO.getAgency());
        campaign.setArchived(campaignDTO.getArchived());
        campaign.setBooked_budget(campaignDTO.getBooked_budget());
        campaign.setCampaign_manager_id(campaignDTO.getCampaign_manager_id());
        campaign.setDaily_cap(campaignDTO.getDaily_cap());
        campaign.setDeliveryType(campaignDTO.getDeliveryType());
        campaign.setEndOn(campaignDTO.getEnd_on());
        campaign.setFrequency_cap(campaignDTO.getFrequency_cap());
        campaign.setFrequency_cap_count(campaignDTO.getFrequency_cap_count());
        campaign.setAgencyFee(campaignDTO.getAgency_fee());
        campaign.setInternal_purchase_order_number(campaignDTO.getInternal_purchase_order_number());
        //campaign.setInventorySources(campaignDTO.getInventory_sources());
        campaign.setInternal_purchase_order_number(campaignDTO.getInternal_purchase_order_number());
        campaign.setTarget(campaignDTO.getTargeting_logic());
        campaign.setManaged(campaignDTO.getManaged());
        campaign.setMedia_budget(campaignDTO.getMedia_budget());
        campaign.setMinimum_ctr_percentage(campaignDTO.getMinimum_ctr_percentage());
        campaign.setMobile_app_filter(campaignDTO.getMobile_app_filter());
        campaign.setMedia_plan_id(campaignDTO.getMedia_plan_id());
        campaign.setName(campaignDTO.getName());
        campaign.setOfferId(campaignDTO.getOffer_id());

        campaign.setRtbOptimizeType(campaignDTO.getRtbOptimizeType());
        campaign.setRtb_optimize_conversion_id(campaignDTO.getRtb_optimize_conversion_id());
        campaign.setSales_manager_id(campaignDTO.getSales_manager_id());
        campaign.setShared_channel_filter(campaignDTO.getShared_channel_filter());
        campaign.setStartOn(campaignDTO.getStart_on());


        campaign.setSupply_types(campaignDTO.getSupply_types());
        //campaign.setRtb_urls(campaignDTO.getRtb_urls());

        //campaign.setCity_ids(campaignDTO.getCity_ids());
        //campaign.setDirect_deal_ids(campaignDTO.getDirect_deal_ids());
        //campaign.setCountry_ids(campaignDTO.getCountry_ids());
        //campaign.setBrowser_ids(campaignDTO.getBrowser_ids());
        //campaign.setDevice_type_ids(campaignDTO.getDevice_type_ids());
        //campaign.setLanguage_ids(campaignDTO.getLanguage_ids());
        //campaign.setOperating_system_ids(campaignDTO.getOperating_system_ids());
        //campaign.setRegion_ids(campaignDTO.getRegion_ids());
        //campaign.setShared_channel_ids(campaignDTO.getShared_channel_ids());
        //campaign.setMobile_app_ids(campaignDTO.getMobile_app_ids());

        return campaign;
}

}
