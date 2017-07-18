package com.pusulait.airsqreen.service.cron;

import com.pusulait.airsqreen.domain.campaign.Publisher;
import com.pusulait.airsqreen.service.PublisherService;
import com.pusulait.airsqreen.service.paltform161.Platform161Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by benan on 7/17/2017.
 */

@Service
@Transactional
public class Plt161CronJobService {

    @Autowired
    private PublisherService publisherService;


    @Scheduled(cron = "0 0 1 * * ?")
    public void updatePlt161Datas() {
        publisherService.updatePublishers();
    }
}
