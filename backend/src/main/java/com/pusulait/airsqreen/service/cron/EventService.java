package com.pusulait.airsqreen.service.cron;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.CampaignSection;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;
import com.pusulait.airsqreen.domain.dto.event.Sistem9PushEventDTO;
import com.pusulait.airsqreen.domain.enums.EventStatus;
import com.pusulait.airsqreen.domain.event.Sistem9PushEvent;
import com.pusulait.airsqreen.repository.campaign.CampaignRepository;
import com.pusulait.airsqreen.repository.campaign.CampaignSectionRepository;
import com.pusulait.airsqreen.repository.event.Sistem9PushEventRepository;
import com.pusulait.airsqreen.service.system9.Sistem9Adapter;
import com.pusulait.airsqreen.service.system9.Sistem9PushEventService;
import com.pusulait.airsqreen.service.viewcount.ViewCountAndPriceService;
import com.pusulait.airsqreen.service.viewcount.ViewCountSpendRequirement;
import com.pusulait.airsqreen.util.DateUtil;
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

        List<Campaign> activeCampaigns = campaignRepository.findAllActive();

        for (Campaign campaign : activeCampaigns) {

            CampaignSection campaignSection = campaign.getCampaignSections().get(0);

            Double paidBudget = viewCountAndPriceService.getTotalSpent(campaign.getId().toString(), campaignSection.getSectionId().toString());

            Plt161Campaign plt161Campaign = (Plt161Campaign) campaign;

            Double remainingBudget = plt161Campaign.getMedia_budget() - (paidBudget);

            if (remainingBudget <= 0 || remainingBudget < campaignSection.getSection().getPrice()) {
                return; // para kalmadıysa yapacak da bişi yok
            }

            Double pricePerShow = campaignSection.getSection().getPrice();

            Double nShowDouble = (remainingBudget / pricePerShow);
            Integer nShow = nShowDouble.intValue();


            boolean inWeekDay = isInWeekDay(plt161Campaign, new Date());

            if (!inWeekDay) {
                return; // çalışma günü değilmiş.
            }

            Boolean isLastDay = DateUtil.isInSameDay(campaign.getEndOn(), new Date());

            int dailyHourCount = plt161Campaign.getTargeting_hour_ids().length;
            int notAvailableHourCount = dailyHourCount - calculateHour(plt161Campaign, new Date(), isLastDay);
            int totalHour = 0;

            for (DateTime date = new DateTime(); date.isBefore(new DateTime(campaign.getEndOn())); date = date.plusDays(1)) {
                if (isInWeekDay(plt161Campaign, date.toDate())) {
                    totalHour += dailyHourCount;
                }
            }
            totalHour -= notAvailableHourCount;

            if (totalHour == 0) {
                return;
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

    private boolean isInWeekDay(Plt161Campaign plt161Campaign, Date date) {

        Long[] weekDayIds = plt161Campaign.getTargeting_weekday_ids();

        boolean inWeekDay = false;

        if (weekDayIds == null) {

            inWeekDay = true;
        } else {
            for (Long weekDayId : weekDayIds) {
                if (weekDayId.intValue() == DateUtil.getDayOfWeek(date)) {
                    inWeekDay = true;
                }
            }
        }
        return inWeekDay;
    }

    private boolean isInDayHour(Plt161Campaign plt161Campaign, Date date) {

        Long[] dayHourIds = plt161Campaign.getTargeting_hour_ids();

        boolean inDayHour = false;

        if (dayHourIds == null) {

            inDayHour = true;
        } else {
            for (Long dayHourId : dayHourIds) {
                if (dayHourId.intValue() == DateUtil.getHourOfDate(date)) {
                    inDayHour = true;
                }
            }
        }
        return inDayHour;
    }

    private Integer calculateHour(Plt161Campaign plt161Campaign, Date date, Boolean isLastDay) {

        if (!isInDayHour(plt161Campaign, date)) {
            return 0;
        }

        Integer campaignEndHour = DateUtil.getHourOfDate(plt161Campaign.getEndOn());
        Integer hour = DateUtil.getHourOfDate(new Date());
        Long[] dayHourIds = plt161Campaign.getTargeting_hour_ids();
        int workableHourCount = 0;

        for (Long dayHourId : dayHourIds) {
            if (hour < dayHourId && !isLastDay) {
                workableHourCount++;
            } else if (campaignEndHour > dayHourId && hour < dayHourId) {
                workableHourCount++;
            }
        }
        return workableHourCount;
    }


    private String calculateActionId() {
        return "";
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

//    private Integer calculateEventSize() {
//        // burda bu saat diliminde toplam kaç event ihtiyacımız var
//
//        return 100;
//    }


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
