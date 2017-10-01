package com.pusulait.airsqreen.domain.dto.security;

import com.pusulait.airsqreen.domain.campaign.CampaignSection;
import com.pusulait.airsqreen.domain.dto.platform.PlatformUserDTO;
import lombok.Data;

import javax.persistence.Column;

/**
 * Created by benan on 8/6/2017.
 */
@Data
public class CampaignSectionDTO {

    private Long campaignId;
    private Long sectionId;
    private Long id;
    private String actionId;
    private String key;
    private Double sspPrice;
    private String trackingToken;



    public static CampaignSectionDTO toDTO(CampaignSection campaignSection) {

        CampaignSectionDTO campaignSectionDTO = new CampaignSectionDTO();

        campaignSectionDTO.setId(campaignSection.getId());
        campaignSectionDTO.setSectionId(campaignSection.getSectionId());
        campaignSectionDTO.setCampaignId(campaignSection.getCampaignId());
        campaignSectionDTO.setKey(campaignSection.getKey());
        campaignSectionDTO.setSspPrice(campaignSection.getSspPrice());
        campaignSectionDTO.setActionId(campaignSection.getActionId());
        return campaignSectionDTO;
    }

    public static CampaignSection toEntity(CampaignSectionDTO campaignSectionDTO) {

        CampaignSection campaignSection = new CampaignSection();
        campaignSection.setId(campaignSectionDTO.getId());
        campaignSection.setSectionId(campaignSectionDTO.getSectionId());
        campaignSection.setCampaignId(campaignSectionDTO.getCampaignId());
        campaignSection.setKey(campaignSectionDTO.getKey());
        campaignSection.setSspPrice(campaignSectionDTO.getSspPrice());
        campaignSection.setActionId(campaignSectionDTO.getActionId());

        return campaignSection;
    }
}
