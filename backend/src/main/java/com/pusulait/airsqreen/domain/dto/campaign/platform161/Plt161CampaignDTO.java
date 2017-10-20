package com.pusulait.airsqreen.domain.dto.campaign.platform161;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;
import com.pusulait.airsqreen.domain.dto.campaign.CampaignDTO;
import com.pusulait.airsqreen.domain.dto.campaign.enums.PricingType;
import com.pusulait.airsqreen.util.EntityUtil;
import lombok.Data;

import java.util.Date;

/**
 * Created by benan on 7/9/2017.
 */

@Data
public class Plt161CampaignDTO extends CampaignDTO {

    private Date updated_at;
    private Long[] filtered_section_ids;
    private Long[] weekday_ids;
    private Long[] hour_ids;
    private Double media_budget;
    private Long platformUserId;
    private Integer frequency_cap;
    private String frequency_cap_type;
    private Integer frequency_cap_count;

    public static Plt161CampaignDTO toDTO(Plt161Campaign campaign) {

        Plt161CampaignDTO campaignDTO = new Plt161CampaignDTO();
        campaignDTO.setHour_ids(EntityUtil.buildLongArray(campaign.getTargeting_hour_ids()));
        campaignDTO.setWeekday_ids(EntityUtil.buildLongArray(campaign.getTargeting_weekday_ids()));
        campaignDTO.setExternalId(campaign.getId());
        campaignDTO.setPricingType(campaign.getPricingType());
        campaignDTO.setActive(campaign.getActive());
        campaignDTO.setMedia_budget(campaign.getMedia_budget());
        campaignDTO.setUpdated_at(campaign.getUpdated_at());
        campaignDTO.setFiltered_section_ids(EntityUtil.buildLongArray(campaign.getFiltered_section_ids()));
        campaignDTO.setStart_on(campaign.getStartOn());
        campaignDTO.setEnd_on(campaign.getEndOn());
        campaignDTO.setPrice(campaign.getPrice());
        campaignDTO.setPlatformUserId(campaign.getPlatformUserId());
        campaignDTO.setFrequency_cap(campaign.getFrequency_cap());
        campaignDTO.setFrequency_cap_type(campaign.getFrequency_cap_type());
        campaignDTO.setFrequency_cap_count(campaign.getFrequency_cap_count());
        return campaignDTO;
    }

    public static Plt161Campaign toEntity(Plt161CampaignDTO campaignDTO) {

        Plt161Campaign campaign = new Plt161Campaign();
        campaign.setTargeting_hour_ids(EntityUtil.buildString(campaignDTO.getHour_ids()));
        campaign.setTargeting_weekday_ids(EntityUtil.buildString(campaignDTO.getWeekday_ids()));
        campaign.setExternalId(campaignDTO.getId());
        campaign.setPricingType(campaignDTO.getPricingType());
        campaign.setActive(campaignDTO.getActive());
        campaign.setMedia_budget(campaignDTO.getMedia_budget());
        campaign.setUpdated_at(campaignDTO.getUpdated_at());
        campaign.setFiltered_section_ids(EntityUtil.buildString(campaignDTO.getFiltered_section_ids()));
        campaign.setStartOn(campaignDTO.getStart_on());
        campaign.setEndOn(campaignDTO.getEnd_on());
        campaign.setPrice(campaignDTO.getPrice());
        campaign.setName(campaignDTO.getName());
        campaign.setPricingType(PricingType.convert(campaignDTO.getPricing_type()));
        campaign.setPlatformUserId(campaignDTO.getPlatformUserId());
        campaign.setFrequency_cap(campaignDTO.getFrequency_cap());
        campaign.setFrequency_cap_type(campaignDTO.getFrequency_cap_type());
        campaign.setFrequency_cap_count(campaignDTO.getFrequency_cap_count());
        return campaign;
    }


    public static Campaign update(Plt161CampaignDTO campaignDTO) {

        Plt161Campaign campaign = new Plt161Campaign();
        campaign.setTargeting_hour_ids(EntityUtil.buildString(campaignDTO.getHour_ids()));
        campaign.setTargeting_weekday_ids(EntityUtil.buildString(campaignDTO.getWeekday_ids()));
        campaign.setExternalId(campaignDTO.getId());
        campaign.setPricingType(campaignDTO.getPricingType());
        campaign.setActive(campaignDTO.getActive());
        campaign.setMedia_budget(campaignDTO.getMedia_budget());
        campaign.setUpdated_at(campaignDTO.getUpdated_at());
        campaign.setFiltered_section_ids(EntityUtil.buildString(campaignDTO.getFiltered_section_ids()));
        campaign.setStartOn(campaignDTO.getStart_on());
        campaign.setEndOn(campaignDTO.getEnd_on());
        campaign.setPrice(campaignDTO.getPrice());
        campaign.setPlatformUserId(campaignDTO.getPlatformUserId());
        campaign.setFrequency_cap(campaignDTO.getFrequency_cap());
        campaign.setFrequency_cap_type(campaignDTO.getFrequency_cap_type());
        campaign.setFrequency_cap_count(campaignDTO.getFrequency_cap_count());
        return campaign;


    }

   

}
