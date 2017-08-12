package com.pusulait.airsqreen.service.cron;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.CampaignSection;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;
import com.pusulait.airsqreen.domain.dto.event.Sistem9PushEventDTO;
import com.pusulait.airsqreen.domain.enums.EventStatus;
import com.pusulait.airsqreen.domain.enums.EventType;
import com.pusulait.airsqreen.domain.event.Sistem9PushEvent;
import com.pusulait.airsqreen.repository.campaign.CampaignRepository;
import com.pusulait.airsqreen.repository.campaign.CampaignSectionRepository;
import com.pusulait.airsqreen.repository.event.Sistem9PushEventRepository;
import com.pusulait.airsqreen.service.system9.Sistem9Adapter;
import com.pusulait.airsqreen.service.system9.Sistem9PushEventService;
import com.pusulait.airsqreen.service.viewcount.ViewCountAndPriceService;
import com.pusulait.airsqreen.service.viewcount.ViewCountSpendRequirement;
import com.pusulait.airsqreen.util.DateUtil;
import com.pusulait.airsqreen.util.EntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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

            Double paidBudget = viewCountAndPriceService.getTotalSpent(plt161Campaign.getExternalId().toString(), campaignSection.getSectionId().toString());

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
            int notAvailableHourCount = dailyHourCount - DateUtil.calculateHour(plt161Campaign, new Date(), isLastDay);
            int totalHour = 0;

            for (DateTime date = new DateTime(); date.isBefore(new DateTime(plt161Campaign.getEndOn())); date = date.plusDays(1)) {
                if (DateUtil.isInWeekDay(plt161Campaign, date.toDate())) {
                    totalHour += dailyHourCount;
                }
            }
            totalHour -= notAvailableHourCount;

            if (totalHour == 0) {
                  continue;
            }

            // günde 1000 gösterim
            Integer showPerHour = nShow / totalHour;

            List<Sistem9PushEvent> sistem9PushEventList = new ArrayList<>();

            List<Long> deviceIdList = new ArrayList<>();

            deviceIdList.add(campaignSection.getDeviceId());

            // Ekranın available olduğu an
            Date screenStartDate = new Date();

            int minutes = 0;
            for (int i = 0; i < showPerHour; i++) {

                Sistem9PushEventDTO pushEventDTO = new Sistem9PushEventDTO();

                pushEventDTO.setEventType(EventType.SISTEM9_PUSH);
                pushEventDTO.setEventStatus(EventStatus.WAITING);
                pushEventDTO.setSlaveId(1L);
                pushEventDTO.setExpireDate(null);
                pushEventDTO.setRunDate(setRunDate(i, 0, screenStartDate));
                pushEventDTO.setDeviceId(calculateDeviceId(i, deviceIdList));
                //pushEventDTO.setActionId(calculateActionId());
                sistem9PushEventService.save(pushEventDTO);

                minutes += 0;
            }
        }
    }





    private Long calculateDeviceId(int i, List<Long> deviceIdList) {
        return deviceIdList.get(i % deviceIdList.size());
    }

    private Date setRunDate(Integer pushNo, Integer period, Date screenStartDate) {

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
