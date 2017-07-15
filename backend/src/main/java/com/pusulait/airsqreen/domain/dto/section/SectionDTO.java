package com.pusulait.airsqreen.domain.dto.section;

import com.pusulait.airsqreen.domain.dto.campaign.enums.PricingType;
import lombok.Data;

import java.util.List;

/**
 * Created by benan on 7/15/2017.
 */
@Data
public class SectionDTO {

    private Long id;// (Number) — The ID of the section.
    private String name; //(String) — The name of section.
    private Double price;// (Number) — The price of section.
    private PricingType pricingType;
    private Long publisher_id;// (Number) — The ID of the publisher that owns the section.
    private Long site_id;// (Number) — The ID of the the site this section belongs to.

    private Long[] size_ids;
    private String advertiser_filter;
    private Long[] filtered_advertiser_ids;
    private String campaign_filter;
    private Long[] filtered_campaign_ids;
    private String[] excluded_pricing_types;
    private Boolean shared;

}
