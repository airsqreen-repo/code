package com.pusulait.airsqreen.service.cron;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.campaign.CampaignSection;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;
import com.pusulait.airsqreen.domain.dto.event.Sistem9PushEventDTO;
import com.pusulait.airsqreen.domain.enums.EventStatus;
import com.pusulait.airsqreen.domain.enums.EventType;
import com.pusulait.airsqreen.domain.event.Sistem9PushEvent;
import com.pusulait.airsqreen.repository.campaign.CampaignRepository;
import com.pusulait.airsqreen.repository.event.Sistem9PushEventRepository;
import com.pusulait.airsqreen.service.system9.Sistem9Adapter;
import com.pusulait.airsqreen.service.system9.Sistem9PushEventService;
import com.pusulait.airsqreen.service.viewcount.ViewCountAndPriceService;
import com.pusulait.airsqreen.util.DateUtil;
import com.pusulait.airsqreen.util.EntityUtil;
import com.pusulait.airsqreen.util.EventUtil;
import com.pusulait.airsqreen.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by benan on 7/16/2017.
 */
@Slf4j
@Service
@Transactional
public class EventService {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private Sistem9PushEventRepository sistem9PushEventRepository;

    @Autowired
    private Sistem9PushEventService sistem9PushEventService;

    @Autowired
    private Sistem9Adapter sistem9Adapter;

    @Autowired
    private ViewCountAndPriceService viewCountAndPriceService;


    // cron = "30 2 * * * ?"
    @Scheduled(cron = "0 0 1 * * ?")
    public void prepareHourlyRecords() {
        generateSistem9Events();
    }

    public void generateSistem9Events() {

        List<Plt161Campaign> activeCampaigns = campaignRepository.findLiveCampaigns();

        for (Plt161Campaign plt161Campaign : activeCampaigns) {

            CampaignSection campaignSection = plt161Campaign.getCampaignSections().get(0);

            Double paidBudget = viewCountAndPriceService.getTotalSpent(plt161Campaign.getExternalId().toString(), campaignSection.getSection().getExternalId().toString());

            Double remainingBudget = plt161Campaign.getMedia_budget() - (paidBudget);

            if (remainingBudget <= 0 || remainingBudget < campaignSection.getSection().getPrice()) {
                continue; // para kalmadıysa yapacak da bişi yok
            }

            Double pricePerShow = campaignSection.getSection().getPrice() / 1000;

            Double nShowDouble = (remainingBudget / pricePerShow);
            Integer nShow = nShowDouble.intValue();


            boolean inWeekDay = DateUtil.isInWeekDay(plt161Campaign, new Date());

            if (!inWeekDay) {
                continue; // çalışma günü değilmiş.
            }

            Boolean isLastDay = DateUtil.isInSameDay(plt161Campaign.getEndOn(), new Date());

            int dailyHourCount = EntityUtil.buildLongArray(plt161Campaign.getTargeting_hour_ids()).length;
            if (StringUtils.isEmpty(plt161Campaign.getTargeting_hour_ids())) {
                dailyHourCount = 24;
            }
            int notAvailableHourCount = dailyHourCount - DateUtil.calculateHour(plt161Campaign, new Date(), isLastDay);
            int totalHour = 0;

            totalHour = DateUtil.calculateTotalHour(plt161Campaign, dailyHourCount, totalHour);
            totalHour -= notAvailableHourCount;

            if (totalHour == 0) {
                continue;
            }

            Integer showPerHour = nShow / totalHour;

            showPerHour = EntityUtil.evaluateUsingFrequencyInformation(plt161Campaign, showPerHour);


            for (int i = 0; i < showPerHour; i++) {
                createNewEvents(plt161Campaign, i);
            }
        }
    }



    private void createNewEvents(Plt161Campaign plt161Campaign, int i) {

        Sistem9PushEventDTO pushEventDTO = new Sistem9PushEventDTO();

        pushEventDTO.setEventType(EventType.SISTEM9_PUSH);
        pushEventDTO.setEventStatus(EventStatus.WAITING);
        pushEventDTO.setSlaveId(1L);
        pushEventDTO.setExpireDate(EventUtil.setExpireDate());
        pushEventDTO.setRunDate(EventUtil.setRunDate());
        pushEventDTO.setPlatformUserId(plt161Campaign.getPlatformUserId());
        pushEventDTO = EventUtil.setDeviceAndSectionId(pushEventDTO, plt161Campaign, i);

        sistem9PushEventService.save(pushEventDTO);

    }


    @Scheduled(cron = "0 0 0 1 * ?")
    public void pushEvents() {

        List<Sistem9PushEvent> weeklyPushEventList = sistem9PushEventRepository.findWaitingEvents();

        for (Sistem9PushEvent event : weeklyPushEventList) {
            sistem9Adapter.push(event);
            event.setEventStatus(EventStatus.DONE);
            sistem9PushEventRepository.save(event);
        }
    }


}
