package com.pusulait.airsqreen.predicate;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.dto.campaign.platform161.Plt161CampaignDTO;
import com.pusulait.airsqreen.domain.dto.campaign.enums.PricingType;

import java.util.function.Predicate;

/**
 */
public class CampaignPredicate {

    public static Predicate<Campaign> pricingTypePredicate(PricingType pricingType){

        return campaign -> (campaign.getPricingType() != null && campaign.getPricingType().equals(pricingType));
    }

    public static Predicate<Plt161CampaignDTO> pricingTypePredicateDTO(PricingType pricingType){

        return campaign -> (campaign.getPricing_type() != null && campaign.getPricing_type().toUpperCase().equals(pricingType.toString()));
    }


}
