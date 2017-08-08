package com.pusulait.airsqreen.domain.dto.campaign;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;
import lombok.Data;

import java.util.Date;

/**
 * Created by benan on 7/9/2017.
 */

@Data
public class Plt161CampaignDTO extends CampaignDTO {

    private Date updated_at;
    private Long[] filtered_section_ids;
    private Long[] weekday_ids;
    private Long[] hour_ids;
    private Double media_budget;

    public static Plt161CampaignDTO toDTO(Plt161Campaign campaign) {

        Plt161CampaignDTO campaignDTO = new Plt161CampaignDTO();
        return campaignDTO;
    }

    public static Plt161Campaign toEntity(Plt161CampaignDTO campaignDTO) {

        Plt161Campaign campaign = new Plt161Campaign();
        campaign.setTargeting_hour_ids(campaignDTO.getHour_ids());
        campaign.setTargeting_weekday_ids(campaignDTO.getWeekday_ids());
        campaign.setExternalId(campaignDTO.getId());
        campaign.setPricingType(campaignDTO.getPricing_type());
        campaign.setActive(campaignDTO.getActive());
        campaign.setMedia_budget(campaignDTO.getMedia_budget());
        campaign.setUpdated_at(campaignDTO.getUpdated_at());
        campaign.setFiltered_section_ids(campaignDTO.getFiltered_section_ids());

        return campaign;
    }

    // Build a comma seperated value string
   /* private static <T> String buildString(T[] values) {

        if (values == null) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder("");

        for (T value : values) {
            stringBuilder.append(value);
            stringBuilder.append(",");
        }

        String result = stringBuilder.toString();

        return result.substring(0, result.length() - 1);

    }*/

    public static Campaign update(Plt161CampaignDTO campaignDTO, Plt161Campaign campaign) {

        campaign.setMedia_budget(campaignDTO.getMedia_budget());
        campaign.setTargeting_weekday_ids(campaignDTO.getWeekday_ids());
        campaign.setTargeting_hour_ids(campaignDTO.getHour_ids());
        campaign.setTargeting_weekday_ids(campaignDTO.getWeekday_ids());
        campaign.setUpdated_at(campaignDTO.getUpdated_at());
        campaign.setFiltered_section_ids(campaignDTO.getFiltered_section_ids());
        return campaign;

        /*campaign.setExternalId(campaignDTO.getId());
        campaign.setExternalId(campaignDTO.getId());
        campaign.setPricing_type(campaignDTO.getPricing_type());
        campaign.setActive(campaignDTO.getActive());
        campaign.setAdvertiser_id(campaignDTO.getAdvertiser_id());
        campaign.setAgency(campaignDTO.getAgency());
        campaign.setArchived(campaignDTO.getArchived());
        campaign.setBooked_budget(campaignDTO.getBooked_budget());
        campaign.setCampaign_manager_id(campaignDTO.getCampaign_manager_id());
        campaign.setDaily_cap(campaignDTO.getDaily_cap());
        campaign.setDeliveryType(campaignDTO.getDeliveryType());
        campaign.setEnd_on(campaignDTO.getEnd_on());
        campaign.setFrequency_cap(campaignDTO.getFrequency_cap());
        campaign.setFrequency_cap_count(campaignDTO.getFrequency_cap_count());
        campaign.setAgencyFee(campaignDTO.getAgency_fee());
        campaign.setInternal_purchase_order_number(campaignDTO.getInternal_purchase_order_number());
        //campaign.setInventorySources(campaignDTO.getInventory_sources());
        campaign.setInternal_purchase_order_number(campaignDTO.getInternal_purchase_order_number());
        campaign.setTarget(campaignDTO.getTargeting_logic());
        campaign.setManaged(campaignDTO.getManaged());
        campaign.setMinimum_ctr_percentage(campaignDTO.getMinimum_ctr_percentage());
        campaign.setMobile_app_filter(campaignDTO.getMobile_app_filter());
        campaign.setMedia_plan_id(campaignDTO.getMedia_plan_id());
        campaign.setName(campaignDTO.getName());
        campaign.setOfferId(campaignDTO.getOffer_id());

        campaign.setRtbOptimizeType(campaignDTO.getRtbOptimizeType());
        campaign.setRtb_optimize_conversion_id(campaignDTO.getRtb_optimize_conversion_id());
        campaign.setSales_manager_id(campaignDTO.getSales_manager_id());
        campaign.setShared_channel_filter(campaignDTO.getShared_channel_filter());
        campaign.setStart_on(campaignDTO.getStart_on());


        campaign.setSupply_types(campaignDTO.getSupply_types());
        campaign.setRtb_urls(campaignDTO.getRtb_urls());

        campaign.setCity_ids(campaignDTO.getCity_ids());
        campaign.setDirect_deal_ids(campaignDTO.getDirect_deal_ids());
        campaign.setCountry_ids(campaignDTO.getCountry_ids());
        campaign.setBrowser_ids(campaignDTO.getBrowser_ids());
        campaign.setDevice_type_ids(campaignDTO.getDevice_type_ids());
        campaign.setLanguage_ids(campaignDTO.getLanguage_ids());
        campaign.setOperating_system_ids(campaignDTO.getOperating_system_ids());
        campaign.setRegion_ids(campaignDTO.getRegion_ids());
        campaign.setShared_channel_ids(campaignDTO.getShared_channel_ids());
        campaign.setMobile_app_ids(campaignDTO.getMobile_app_ids());*/


    }
}
