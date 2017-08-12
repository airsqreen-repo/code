package com.pusulait.airsqreen.util;

import com.pusulait.airsqreen.domain.campaign.CampaignSection;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;
import com.pusulait.airsqreen.domain.dto.event.Sistem9PushEventDTO;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventUtil {

    public static Sistem9PushEventDTO setDeviceAndSectionId(Sistem9PushEventDTO pushEventDTO, Plt161Campaign plt161Campaign, int i) {

        List<CampaignSection> campaignSectionList = plt161Campaign.getCampaignSections();
        CampaignSection campaignSection = campaignSectionList.get(i % campaignSectionList.size());
        pushEventDTO.setCampaignSectionId(campaignSection.getId());
        pushEventDTO.setDeviceId(campaignSection.getDeviceId());
        return pushEventDTO;
    }

    public static Date setRunDate(Integer pushNo, Integer period, Date screenStartDate) {

        Long timeInDay = pushNo * period + screenStartDate.getTime();
        Long hour = timeInDay / 3600;
        Long minute = timeInDay % 60;
        Long second = timeInDay % 3600;

        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(new Date().getTime());
        c1.set(Calendar.HOUR_OF_DAY, hour.intValue());
        c1.set(Calendar.MINUTE, minute.intValue());
        c1.set(Calendar.SECOND, second.intValue());

        return c1.getTime();

    }
}
