package com.pusulait.airsqreen.domain.dto.section;

import com.pusulait.airsqreen.domain.campaign.Section;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Section;
import com.pusulait.airsqreen.domain.dto.campaign.enums.PricingType;
import lombok.Data;

/**
 * Created by benan on 7/15/2017.
 */
@Data
public class SectionDTO {

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


    public static Plt161Section toEntity(SectionDTO sectionDTO) {

        Plt161Section section = new Plt161Section();

        section.setExternalId(sectionDTO.getId());
        section.setName(sectionDTO.getName());
        section.setPrice(sectionDTO.getPrice());
        section.setPricingType(PricingType.convert(sectionDTO.getPricing_type()));
        section.setPublisher_id(sectionDTO.getPublisher_id());
        //section.setSite_id(sectionDTO.getSite_id());
        //section.setSize_ids(sectionDTO.getSize_ids());
        section.setAdvertiser_filter(sectionDTO.getAdvertiser_filter());
        //section.setFiltered_advertiser_ids(sectionDTO.getFiltered_advertiser_ids());
        section.setCampaign_filter(sectionDTO.getCampaign_filter());
        //section.setFiltered_campaign_ids(sectionDTO.getFiltered_campaign_ids());
        //section.setExcluded_pricing_types(sectionDTO.getExcluded_pricing_types());
        section.setShared(sectionDTO.getShared());

        return section;
    }

    public static Section update(SectionDTO sectionDTO, Plt161Section section) {

        section.setExternalId(sectionDTO.getId());
        section.setName(sectionDTO.getName());
        section.setPrice(sectionDTO.getPrice());
        section.setPricingType(sectionDTO.getPricingType());
        section.setPublisher_id(sectionDTO.getPublisher_id());
        //section.setSite_id(sectionDTO.getSite_id());
        //section.setSize_ids(sectionDTO.getSize_ids());
        section.setAdvertiser_filter(sectionDTO.getAdvertiser_filter());
        //section.setFiltered_advertiser_ids(sectionDTO.getFiltered_advertiser_ids());
        section.setCampaign_filter(sectionDTO.getCampaign_filter());
        //section.setFiltered_campaign_ids(sectionDTO.getFiltered_campaign_ids());
        //section.setExcluded_pricing_types(sectionDTO.getExcluded_pricing_types());
        section.setShared(sectionDTO.getShared());

        return section;

    }
}
