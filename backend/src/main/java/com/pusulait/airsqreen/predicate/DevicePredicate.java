package com.pusulait.airsqreen.predicate;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.dto.campaign.Plt161CampaignDTO;
import com.pusulait.airsqreen.domain.dto.campaign.enums.PricingType;
import com.pusulait.airsqreen.domain.view.ViewActiveDevice;

import java.util.function.Predicate;

/**
 */
public class DevicePredicate {

    public static Predicate<ViewActiveDevice> deviceIdPredicate(Long deviceId){

        return activeDevice -> (activeDevice.getDeviceId() != null && activeDevice.getDeviceId().equals(deviceId));
    }


}
