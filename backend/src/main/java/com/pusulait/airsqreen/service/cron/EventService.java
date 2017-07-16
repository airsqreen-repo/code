package com.pusulait.airsqreen.service.cron;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by benan on 7/16/2017.
 */

@Service
@Transactional
public class EventService {

    @Scheduled(cron = "0 0 1 * * ?")
    public void prepareHourlyRecords(){

    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void pushEvents(){


    }


}
