package com.pusulait.airsqreen.domain.dto.campaign;

import com.pusulait.airsqreen.config.constants.Sequences;
import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.CampaignConstraint;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;
import com.pusulait.airsqreen.domain.dto.campaign.enums.PricingType;
import com.pusulait.airsqreen.domain.enums.CampaignConstraintFilter;
import com.pusulait.airsqreen.domain.enums.CampaignConstraintType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by benan on 7/23/2017.
 */
@Data
public class CampaignConstraintDTO {

    private Long id;
    private Campaign campaign;
    private Long campaignId;
    private CampaignConstraintType campaignConstraintType;
    private CampaignConstraintFilter campaignConstraintFilter;
    private String filter_detail;



    public static CampaignConstraintDTO toDTO(CampaignConstraint campaignConstraint) {

        CampaignConstraintDTO campaignDTO = new CampaignConstraintDTO();
        campaignDTO.setId(campaignConstraint.getId());
        campaignDTO.setCampaignId(campaignConstraint.getCampaignId());
        campaignDTO.setCampaignConstraintType(campaignConstraint.getCampaignConstraintType());
        campaignDTO.setCampaignConstraintFilter(campaignConstraint.getCampaignConstraintFilter());
        campaignDTO.setFilter_detail(campaignConstraint.getFilter_detail());
        return campaignDTO;
    }

    public static CampaignConstraint toEntity(CampaignConstraintDTO campaignConstraintDTO) {

        CampaignConstraint campaignConstraint = new CampaignConstraint();
        campaignConstraint.setId(campaignConstraintDTO.getId());
        campaignConstraint.setCampaignId(campaignConstraintDTO.getCampaignId());
        campaignConstraint.setCampaignConstraintType(campaignConstraintDTO.getCampaignConstraintType());
        campaignConstraint.setCampaignConstraintFilter(campaignConstraintDTO.getCampaignConstraintFilter());
        campaignConstraint.setFilter_detail(campaignConstraintDTO.getFilter_detail());
        return campaignConstraint;
    }
}
