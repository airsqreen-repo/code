package com.pusulait.airsqreen.domain.dto.campaign;

import com.pusulait.airsqreen.domain.campaign.Section;
import com.pusulait.airsqreen.domain.dto.campaign.enums.PricingType;
import lombok.Data;


/**
 * Created by ferhat on 7/15/2017.
 */
@Data
public class SectionDTO {

    private Long id;
    private Long externalId;
    private String name; 
    private Double price;
    private PricingType pricingType;




    public static SectionDTO toDTO(Section section) {

        SectionDTO dto = new SectionDTO();

        dto.setId(section.getId());
        dto.setExternalId(section.getExternalId());
        dto.setName(section.getName());
        dto.setPrice(section.getPrice());
        dto.setPricingType(section.getPricingType());

        return dto;
    }



}
