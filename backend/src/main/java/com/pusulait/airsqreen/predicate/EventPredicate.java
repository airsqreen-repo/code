package com.pusulait.airsqreen.predicate;

import com.pusulait.airsqreen.domain.event.Sistem9PushEvent;
import com.pusulait.airsqreen.domain.view.ViewActiveDevice;

import java.util.function.Predicate;

/**
 */
public class EventPredicate {

    public static Predicate<Sistem9PushEvent> deviceIdPredicate(Long deviceId) {
        return pushEvent -> (pushEvent.getDeviceId() != null && pushEvent.getDeviceId().equals(deviceId));
    }


}
