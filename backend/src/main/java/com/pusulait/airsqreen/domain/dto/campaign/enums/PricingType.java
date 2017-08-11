package com.pusulait.airsqreen.domain.dto.campaign.enums;

/**
 * Created by benan on 7/9/2017.
 */
public enum PricingType {

    CPM, CPC, CPA;


    public static PricingType convert(String pricingType){
        if (pricingType == null)
        return null;

        if(pricingType.toUpperCase().equals(CPM.toString()))
            return CPM;
        if(pricingType.toUpperCase().equals(CPC.toString()))
            return CPC;

        if(pricingType.toUpperCase().equals(CPA.toString()))
            return CPA;
          else return null;
    }

/** cpm: Cost per thousand is one of the online payment models by which advertisers pays for every 1000 impressions of their advertisement
* cpc: Cost per click is one of the online payment models by which advertisers pays for each click through made on their advertisement.
* cpa: Cost per action is one of the online payment models by which advertisers pays for ever action (sale or registration) completed as a result of a visitor clicking on their advertisement.
*/
}
