package com.pusulait.airsqreen.domain.dto.campaign;

import com.pusulait.airsqreen.domain.campaign.CampaignSection;
import com.pusulait.airsqreen.domain.dto.device.DeviceDTO;
import lombok.Data;


/**
 * Created by benan on 8/6/2017.
 */
@Data
public class CampaignSectionDTO {
    private Long id;
    private Long campaignId;
    private SectionDTO section;
    private Long sectionId;
    private DeviceDTO device;
    private Long deviceId;
    private String actionId;
    private String key;
    private Double sspPrice;

    private String trackingToken;


    public static CampaignSectionDTO toDTO(CampaignSection campaignSection) {

        CampaignSectionDTO campaignSectionDTO = new CampaignSectionDTO();

        campaignSectionDTO.setId(campaignSection.getId());
        campaignSectionDTO.setSectionId(campaignSection.getSectionId());
        if(campaignSection.getSectionId() != null)
        campaignSectionDTO.setSection(SectionDTO.toDTO(campaignSection.getSection()));
        campaignSectionDTO.setCampaignId(campaignSection.getCampaignId());
        campaignSectionDTO.setDeviceId(campaignSection.getDeviceId());
        if(campaignSection.getDeviceId() != null)
        campaignSectionDTO.setDevice(DeviceDTO.toDTO(campaignSection.getDevice()));
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
