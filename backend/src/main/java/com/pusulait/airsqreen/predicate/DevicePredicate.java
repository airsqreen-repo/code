package com.pusulait.airsqreen.predicate;

import com.pusulait.airsqreen.domain.view.ViewActiveDevice;

import java.util.function.Predicate;

/**
 */
public class DevicePredicate {

    public static Predicate<ViewActiveDevice> deviceIdPredicate(Long deviceId){

        return activeDevice -> (activeDevice.getDeviceId() != null && activeDevice.getDeviceId().equals(deviceId));
    }


}
