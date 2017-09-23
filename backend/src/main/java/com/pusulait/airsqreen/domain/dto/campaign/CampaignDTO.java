package com.pusulait.airsqreen.domain.dto.campaign;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;
import com.pusulait.airsqreen.domain.dto.campaign.enums.PricingType;
import com.pusulait.airsqreen.util.EntityUtil;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by benan on 7/23/2017.
 */
@Data
public class CampaignDTO {

    private Long id;

    private Long externalId;

    private String uniqueCampaignUId;

    private String name;

    private Boolean active;

    private String pricing_type;

    private PricingType pricingType;

    private String target;
    private Date start_on;

    private Date end_on;

    private Double price;


    public static CampaignDTO toDTO(Campaign campaign) {

        CampaignDTO campaignDTO = new CampaignDTO();
        campaignDTO.setExternalId(campaign.getId());
        campaignDTO.setPricingType(campaign.getPricingType());
        campaignDTO.setActive(campaign.getActive());
        campaignDTO.setStart_on(campaign.getStartOn());
        campaignDTO.setEnd_on(campaign.getEndOn());
        campaignDTO.setPrice(campaign.getPrice());
        return campaignDTO;
    }

    public static Campaign toEntity(CampaignDTO campaignDTO) {

        Plt161Campaign campaign = new Plt161Campaign();
        campaign.setExternalId(campaignDTO.getId());
        campaign.setPricingType(campaignDTO.getPricingType());
        campaign.setActive(campaignDTO.getActive());
        campaign.setStartOn(campaignDTO.getStart_on());
        campaign.setEndOn(campaignDTO.getEnd_on());
        campaign.setPrice(campaignDTO.getPrice());
        campaign.setName(campaignDTO.getName());
        campaign.setPricingType(PricingType.convert(campaignDTO.getPricing_type()));
        return campaign;
    }
}
