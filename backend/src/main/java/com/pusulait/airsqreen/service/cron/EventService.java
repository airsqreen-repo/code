package com.pusulait.airsqreen.service.cron;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.CampaignConstraint;
import com.pusulait.airsqreen.domain.campaign.CampaignSection;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;
import com.pusulait.airsqreen.domain.campaign.sistem9.Device;
import com.pusulait.airsqreen.domain.campaign.sistem9.DeviceConstraint;
import com.pusulait.airsqreen.domain.dto.event.Sistem9PushEventDTO;
import com.pusulait.airsqreen.domain.enums.*;
import com.pusulait.airsqreen.domain.event.Sistem9PushEvent;
import com.pusulait.airsqreen.domain.view.ViewActiveDevice;
import com.pusulait.airsqreen.predicate.EventPredicate;
import com.pusulait.airsqreen.repository.campaign.CampaignRepository;
import com.pusulait.airsqreen.repository.device.DeviceRepository;
import com.pusulait.airsqreen.repository.event.Sistem9PushEventRepository;
import com.pusulait.airsqreen.repository.view.ViewActiveDeviceRepository;
import com.pusulait.airsqreen.service.campaign.CampaignConstraintService;
import com.pusulait.airsqreen.service.system9.Sistem9Adapter;
import com.pusulait.airsqreen.service.system9.Sistem9PushEventService;
import com.pusulait.airsqreen.service.viewcount.ViewCountAndPriceService;
import com.pusulait.airsqreen.service.viewcount.ViewCountService;
import com.pusulait.airsqreen.service.weather.WeatherService;
import com.pusulait.airsqreen.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private ViewCountService viewCountService;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ViewActiveDeviceRepository viewActiveDeviceRepository;

    @Autowired
    private CampaignConstraintService campaignConstraintService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void prepareHourlyRecords() {
        generateSistem9Events();
    }

    public void generateSistem9Events() {


        List<Plt161Campaign> activeCampaigns = campaignRepository.findLiveCampaigns();

        for (Plt161Campaign plt161Campaign : activeCampaigns) {

            CampaignSection campaignSection = plt161Campaign.getCampaignSections().get(0);

            if(campaignConstraintService.campaignControlsPassed(campaignSection)){
                continue;
            }

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
        pushEventDTO = EventUtil.setDeviceActionAndSectionId(pushEventDTO, plt161Campaign, i);
        sistem9PushEventService.save(pushEventDTO);

    }


    //@Scheduled(cron = "0 0 0 1 * ?")
    @Scheduled(fixedRate = 5 * 1000)
    public void pushEvents() throws Exception {

        //weatherService.getTempWithGeoCoordinates()

        List<Sistem9PushEvent> pushEventList = sistem9PushEventRepository.findWaitingEvents();

        List<ViewActiveDevice> activeDevices = viewActiveDeviceRepository.findAll();

        for (ViewActiveDevice viewActiveDevice : activeDevices) {

            Device device = deviceRepository.findOne(viewActiveDevice.getDeviceId());

            if (deviceControlsPassed(device)) {

                List<Sistem9PushEvent> sistem9PushEventList = pushEventList.stream().filter(EventPredicate.deviceIdPredicate(viewActiveDevice.getDeviceId())).collect(Collectors.toList());

                if (sistem9PushEventList.size() > 0) {

                    Sistem9PushEvent event = sistem9PushEventList.get(RandomUtil.generateRandomNumber(sistem9PushEventList.size()));

                    if (event != null) {
                        String result = sistem9Adapter.pushEvent(event);
                        if (result.contains(Constants.SISTEM9_SUCCESS_RESULT)) {
                            event.setResult(result);
                            /*String token = viewCountService.getTrackToken(event.getCampaignSection().getCampaign().getExternalId().toString(),
                                    event.getCampaignSection().getSection().getExternalId().toString());
                            viewCountService.incrementViewCount(token);*/
                            event.setEventStatus(EventStatus.DONE);
                            sistem9PushEventRepository.save(event);
                        } else {
                            event.setResult(result);
                            event.setEventStatus(EventStatus.ERROR);
                            sistem9PushEventRepository.save(event);
                        }
                    }
                }
            }
        }
    }


    //faz 2 de hem event seçimi uygun devicelar ve device seçimi de uygun deviceların seçimi yapılacak
    private Boolean deviceControlsPassed(Device device) {

        Boolean passed = true;

        for (DeviceConstraint deviceConstraint : device.getDeviceConstraintList()) {
            if (deviceConstraint.getDeviceConstraintType().equals(DeviceConstraintType.DYNAMIC_TIME_FILTER)) {
                if (deviceConstraint.getDeviceConstraintFilter().equals(DeviceConstraintFilter.INCLUDE)) {

                    String[] intervalArray = deviceConstraint.getFilter_detail().split("-");

                    if (DateUtil.getMinuteOfDate(new Date()) > Integer.valueOf(intervalArray[0])
                            && DateUtil.getMinuteOfDate(new Date()) < Integer.valueOf(intervalArray[1])) {
                        passed = false;
                    }
                }/* else if (deviceConstraint.getDeviceConstraintFilter().equals(DeviceConstraintFilter.EXCLUDE)) {

                    String[] intervalArray = deviceConstraint.getFilter_detail().split("-");

                    if(DateUtil.getMinuteOfDate(new Date()) > Integer.valueOf(intervalArray[0])
                            &&  DateUtil.getMinuteOfDate(new Date()) < Integer.valueOf(intervalArray[1])){
                        passed = true;
                    }
                }*/

            }
        }

        return passed;
    }




}
