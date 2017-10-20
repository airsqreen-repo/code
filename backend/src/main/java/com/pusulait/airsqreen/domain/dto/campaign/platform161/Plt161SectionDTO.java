package com.pusulait.airsqreen.domain.dto.campaign.platform161;

import com.pusulait.airsqreen.domain.campaign.CampaignSection;
import com.pusulait.airsqreen.domain.campaign.Section;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Section;
import com.pusulait.airsqreen.domain.dto.campaign.CampaignSectionDTO;
import com.pusulait.airsqreen.domain.dto.campaign.enums.PricingType;
import lombok.Data;

/**
 * Created by benan on 7/15/2017.
 */
@Data
public class Plt161SectionDTO {

    private Long id;// (Number) — The ID of the section.
    private String name; //(String) — The name of section.
    private Double price;// (Number) — The price of section.
    private PricingType pricingType;
    private String pricing_type;
    private Long publisher_id;// (Number) — The ID of the publisher that owns the section.
    private Long site_id;// (Number) — The ID of the the site this section belongs to.
    private Long[] size_ids;
    private String advertiser_filter;
    private Long[] filtered_advertiser_ids;
    private String campaign_filter;
    private Long[] filtered_campaign_ids;
    private String[] excluded_pricing_types;
    private Boolean shared;




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

    public static Plt161Section toEntity(Plt161SectionDTO plt161SectionDTO) {

        Plt161Section section = new Plt161Section();
        section.setExternalId(plt161SectionDTO.getId());
        section.setName(plt161SectionDTO.getName());
        section.setPrice(plt161SectionDTO.getPrice());
        section.setPricingType(PricingType.convert(plt161SectionDTO.getPricing_type()));
        section.setPublisher_id(plt161SectionDTO.getPublisher_id());
        section.setAdvertiser_filter(plt161SectionDTO.getAdvertiser_filter());
        section.setCampaign_filter(plt161SectionDTO.getCampaign_filter());
        section.setShared(plt161SectionDTO.getShared());

        return section;
    }

    public static Section update(Plt161SectionDTO plt161SectionDTO, Plt161Section section) {

        section.setExternalId(plt161SectionDTO.getId());
        section.setName(plt161SectionDTO.getName());
        section.setPrice(plt161SectionDTO.getPrice());
        section.setPricingType(plt161SectionDTO.getPricingType());
        section.setPublisher_id(plt161SectionDTO.getPublisher_id());
        section.setAdvertiser_filter(plt161SectionDTO.getAdvertiser_filter());
        section.setCampaign_filter(plt161SectionDTO.getCampaign_filter());
        section.setShared(plt161SectionDTO.getShared());

        return section;

    }
}
