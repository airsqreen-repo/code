package com.pusulait.airsqreen.service.cron;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;
import com.pusulait.airsqreen.domain.dto.event.Sistem9PushEventDTO;
import com.pusulait.airsqreen.domain.enums.EventStatus;
import com.pusulait.airsqreen.domain.event.Sistem9PushEvent;
import com.pusulait.airsqreen.repository.campaign.CampaignRepository;
import com.pusulait.airsqreen.repository.event.Sistem9PushEventRepository;
import com.pusulait.airsqreen.service.CampaignService;
import com.pusulait.airsqreen.service.system9.Sistem9PushEventService;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by benan on 7/16/2017.
 */

@Service
@Transactional
public class EventService {

    private CampaignRepository campaignRepository;

    @Autowired
    private Sistem9PushEventService sistem9PushEventService;

    //TODO: galiba saatlik değil de günlük gösterimleri hesaplamalıyız.
    // örneğin her gün 2:30 da
    // cron = "30 2 * * * ?"
    @Scheduled(cron = "0 0 1 * * ?")
    public void prepareHourlyRecords() {
        generateSistem9Events();
    }

    private void generateSistem9Events() {

        List<Campaign> allCampaigns = campaignRepository.findAllActive();

        for (Campaign campaign : allCampaigns) {

            if (new Date().before(campaign.getStartOn()) ||
                    new Date().after(campaign.getEndOn())) {
                continue;
            }

            // TODO: Bütçeden eksilterek gideceğiz. Budget nereden gelecek?
            // Remaining budget nasıl hesaplayacağız?
            BigDecimal budget = BigDecimal.valueOf(30000);

            // 1 CPM = 1000 TL, 1 gösterim 1 TL
            BigDecimal pricePerShow = BigDecimal.ONE;

            // toplam gösterim sayısı: 30000
            Long nShow = budget.divide(pricePerShow).longValue();

            // days = 30 gün olsun
            int days = Days.daysBetween(new LocalDate(campaign.getStartOn()),
                    new LocalDate(campaign.getEndOn())).getDays();

            // günde 1000 gösterim
            Long showPerDay = nShow / days;

            //TODO: Kaç ekrana dağıtacağını nasıl anlayacağız?
            // Bunlar hangi ekranlar? (device_id?)
            int numberOfScreens = 10;


            //TODO: Elimizde hangi ekranlar olduğunu nerden anlayacağız?

            // her ekran günde 100 gösterim yapacak
            Long dailyShowPerScreen = showPerDay / numberOfScreens;

            //TODO: available hours nereden geliyor?
            int hoursAvailablePerScreen = 6;

            int minutesPerScreen = hoursAvailablePerScreen * 60;

            // 360 / 100 = 4 dakikada 1 gösterim
            long nMinutesToShow = minutesPerScreen / dailyShowPerScreen;

            // 60 / 4 = 12    (1 saatte 12 gösterim eder)
            long showPerHour = 60 / nMinutesToShow;


            List<Sistem9PushEvent> sistem9PushEventList = new ArrayList<>();

            // Ekranın available olduğu an
            DateTime screenStartDate = new DateTime(new Date());

            int minutes = 0;
            for (int i = 0; i < showPerHour; i++) {

                Sistem9PushEventDTO pushEventDTO = new Sistem9PushEventDTO();

                pushEventDTO.setPassword("");
                pushEventDTO.setUsername("");
                pushEventDTO.setEventStatus(EventStatus.WAITING);
                pushEventDTO.setSlaveId(1L);
                pushEventDTO.setExpireDate(null);
                pushEventDTO.setRunDate(screenStartDate.toDate());
                pushEventDTO.setDeviceId(calculateDeviceId());
                pushEventDTO.setActionId(calculateActionId());
                sistem9PushEventService.save(pushEventDTO);

                minutes += nMinutesToShow;
            }


        }


    }

    private String calculateActionId() {
        return "";
    }

    private Integer calculateDeviceId() {
        //burada hangi device seçilecek o random belirlenecek
        return 0;
    }

    private Date calculateRunDate() {
        return new Date();
    }

    private Integer calculateEventSize() {
        // burda bu saat diliminde toplam kaç event ihtiyacımız var

        return 100;
    }


    @Scheduled(cron = "0 0 0 1 * ?")
    public void pushEvents() {


    }


}
