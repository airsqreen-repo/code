package com.pusulait.airsqreen.service.campaign;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.CampaignConstraint;
import com.pusulait.airsqreen.domain.campaign.CampaignSection;
import com.pusulait.airsqreen.domain.campaign.sistem9.Device;
import com.pusulait.airsqreen.domain.dto.weather.WeatherDTO;
import com.pusulait.airsqreen.domain.enums.CampaignConstraintFilter;
import com.pusulait.airsqreen.domain.enums.CampaignConstraintType;
import com.pusulait.airsqreen.service.weather.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class CampaignConstraintService {

    @Autowired
    private WeatherService weatherService;

    public Boolean campaignControlsPassed(CampaignSection campaignSection) {

        Campaign campaign = campaignSection.getCampaign();
        Device device = campaignSection.getDevice();
        Boolean result = false;

        for (CampaignConstraint campaignConstraint : campaign.getCampaignConstraints()) {

            if (campaignConstraint.getCampaignConstraintType().equals(CampaignConstraintType.TEMPERATURE)) {

                WeatherDTO weatherDTO = weatherService.getTempWithGeoCoordinates(device.getLatitude(), device.getLongitude(), true);

                result = result & checkConstraintFilter(campaignConstraint, weatherDTO);

            }
        }

        return true;
    }

    private Boolean checkConstraintFilter(CampaignConstraint campaignConstraint,  WeatherDTO weatherDTO) {

        double temperatureValue = Double.valueOf(campaignConstraint.getFilter_detail());

        double weatherTemp = weatherDTO.getTemp().doubleValue();


        if (campaignConstraint.getCampaignConstraintFilter().equals(CampaignConstraintFilter.BIGGER)) {
            if (temperatureValue > weatherTemp) {
                return true;
            }
        }
        if (campaignConstraint.getCampaignConstraintFilter().equals(CampaignConstraintFilter.BIGGER_EQUAL)) {
            if (temperatureValue > weatherTemp || temperatureValue == weatherTemp) {
                return true;
            }
        }

        if (campaignConstraint.getCampaignConstraintFilter().equals(CampaignConstraintFilter.EQUAL)) {
            if (temperatureValue == weatherTemp) {
                return true;
            }
        }

        if (campaignConstraint.getCampaignConstraintFilter().equals(CampaignConstraintFilter.SMALLER)) {
            if (temperatureValue < weatherTemp) {
                return true;
            }
        }

        if (campaignConstraint.getCampaignConstraintFilter().equals(CampaignConstraintFilter.SMALLER_EQUAL)) {
            if (temperatureValue < weatherTemp || temperatureValue == weatherTemp) {
                return true;
            }
        }

        return false;
    }

}
