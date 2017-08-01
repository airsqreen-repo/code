package com.pusulait.airsqreen.service.cron;

import com.pusulait.airsqreen.domain.dto.campaign.CampaignDTO;
import com.pusulait.airsqreen.domain.dto.event.Sistem9PushEventDTO;
import com.pusulait.airsqreen.domain.enums.EventStatus;
import com.pusulait.airsqreen.domain.event.Sistem9PushEvent;
import com.pusulait.airsqreen.repository.campaign.CampaignRepository;
import com.pusulait.airsqreen.repository.event.Sistem9PushEventRepository;
import com.pusulait.airsqreen.service.system9.Sistem9Adapter;
import com.pusulait.airsqreen.service.system9.Sistem9PushEventService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
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
    private EntityManager entityManager;

    //TODO: galiba saatlik değil de günlük gösterimleri hesaplamalıyız.
    // örneğin her gün 2:30 da
    // cron = "30 2 * * * ?"
    @Scheduled(cron = "0 0 1 * * ?")
    public void prepareHourlyRecords() {
        generateSistem9Events();
    }

    public void generateSistem9Events() {

        //List<Campaign> allCampaigns = campaignRepository.findAllActive();

        List<Object[]> objects = entityManager.createNamedQuery("findAllActive").getResultList();
        List<CampaignDTO> activeCampaigns = new ArrayList<>();

        for (Object o[] : objects){

            CampaignDTO campaignDTO = new CampaignDTO();
            campaignDTO.setId(((BigInteger) o[0] ).longValue());
            campaignDTO.setName((String) o[11]);
            campaignDTO.setStartOn((Timestamp)o[14]);
            campaignDTO.setEndOn((Timestamp)o[9]);
            activeCampaigns.add(campaignDTO);
        }


        for (CampaignDTO campaign : activeCampaigns) {

            // TODO: Bütçeden eksilterek gideceğiz. Budget nereden gelecek?
            // Remaining budget nasıl hesaplayacağız?
            BigDecimal budget = BigDecimal.valueOf(30000);

            // 1 CPM = 1000 TL, 1 gösterim 1 TL
            BigDecimal pricePerShow = BigDecimal.ONE;

            // toplam gösterim sayısı: 30000
            Integer nShow = budget.divide(pricePerShow).intValue();

            // days = 30 gün olsun
            Integer days = Days.daysBetween(new LocalDate(campaign.getStartOn()), new LocalDate(campaign.getEndOn())).getDays();

            // günde 1000 gösterim
            Integer showPerDay = nShow / days;

            //TODO: Kaç ekrana dağıtacağını nasıl anlayacağız?
            // Bunlar hangi ekranlar? (device_id?)
            int numberOfScreens = 10;
            // bütün devicelar için insert olmalı

            //TODO: Elimizde hangi ekranlar olduğunu nerden anlayacağız?

            // her ekran günde 100 gösterim yapacak
            Integer dailyShowPerScreen = showPerDay / numberOfScreens;

            //TODO: available hours nereden geliyor?
            int hoursAvailablePerScreen = 6;

            int secondsPerScreen = hoursAvailablePerScreen * 60 * 60;

            // 360 / 100 = 4 dakikada 1 gösterim
            Integer period = (secondsPerScreen / dailyShowPerScreen);

            // 60 / 4 = 12    (1 saatte 12 gösterim eder)
            long showPerHour = dailyShowPerScreen / hoursAvailablePerScreen;


            List<Sistem9PushEvent> sistem9PushEventList = new ArrayList<>();

            // Ekranın available olduğu an
            Date screenStartDate = new Date();

            int minutes = 0;
            for (int i = 0; i < showPerHour; i++) {

                Sistem9PushEventDTO pushEventDTO = new Sistem9PushEventDTO();

                pushEventDTO.setPassword("");
                pushEventDTO.setUsername("");
                pushEventDTO.setEventStatus(EventStatus.WAITING);
                pushEventDTO.setSlaveId(1L);
                pushEventDTO.setExpireDate(null);
                pushEventDTO.setRunDate(setRunDate(i, period, screenStartDate));
                pushEventDTO.setDeviceId(calculateDeviceId());
                pushEventDTO.setActionId(calculateActionId());
                sistem9PushEventService.save(pushEventDTO);

                minutes += period;
            }
        }
    }

    private String calculateActionId() {
        return "";
    }

    private Integer calculateDeviceId() {
        //burada hangi device seçilecek o random belirlenecek
        // TODO: elimizdeki device id'ler hangileri?

        return new Random().nextInt();

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
        }
    }


}
