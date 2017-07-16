package com.pusulait.airsqreen.service.cron;

import com.pusulait.airsqreen.domain.dto.event.Sistem9PushEventDTO;
import com.pusulait.airsqreen.domain.enums.EventStatus;
import com.pusulait.airsqreen.domain.event.Sistem9PushEvent;
import com.pusulait.airsqreen.repository.event.Sistem9PushEventRepository;
import com.pusulait.airsqreen.service.system9.Sistem9PushEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by benan on 7/16/2017.
 */

@Service
@Transactional
public class EventService {


    @Autowired
    private Sistem9PushEventService sistem9PushEventService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void prepareHourlyRecords() {
        generateSistem9Events();
    }

    private void generateSistem9Events() {


        List<Sistem9PushEvent> sistem9PushEventList = new ArrayList<>();

        for (int i = 0; i < calculateEventSize(); i++) {

            Sistem9PushEventDTO pushEventDTO = new Sistem9PushEventDTO();

            pushEventDTO.setPassword("");
            pushEventDTO.setUsername("");
            pushEventDTO.setEventStatus(EventStatus.WAITING);
            pushEventDTO.setSlaveId(1L);
            pushEventDTO.setExpireDate(null);
            pushEventDTO.setRunDate(calculateRunDate());
            pushEventDTO.setDeviceId(calculateDeviceId());
            pushEventDTO.setActionId(calculateActionId());
            sistem9PushEventService.save(pushEventDTO);
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
