package com.pusulait.airsqreen.util;

import com.pusulait.airsqreen.domain.campaign.CampaignSection;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;
import com.pusulait.airsqreen.domain.dto.event.Sistem9PushEventDTO;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventUtil {

    public static Sistem9PushEventDTO setDeviceActionAndSectionId(Sistem9PushEventDTO pushEventDTO, Plt161Campaign plt161Campaign, int i) {

        List<CampaignSection> campaignSectionList = plt161Campaign.getCampaignSections();
        CampaignSection campaignSection = campaignSectionList.get(i % campaignSectionList.size());
        pushEventDTO.setCampaignSectionId(campaignSection.getId());
        pushEventDTO.setDeviceId(campaignSection.getDeviceId());
        pushEventDTO.setActionId(campaignSection.getActionId());
        return pushEventDTO;
    }

    public static Date setRunDate() {

        Long time = new Date().getTime();
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(time);
        c1.set(Calendar.HOUR_OF_DAY, DateUtil.getHourOfDate(new Date()));
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);

        return c1.getTime();
    }

    public static Date setExpireDate( ) {

        Long time = new Date().getTime();
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(time);
        c1.set(Calendar.HOUR_OF_DAY, DateUtil.getHourOfDate(new Date()));
        c1.set(Calendar.MINUTE, 59);
        c1.set(Calendar.SECOND, 59);

        return c1.getTime();


    }
}
