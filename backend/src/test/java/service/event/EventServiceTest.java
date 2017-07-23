package service.event;

import com.pusulait.airsqreen.Application;
import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.repository.campaign.CampaignRepository;
import com.pusulait.airsqreen.service.CampaignService;
import com.pusulait.airsqreen.service.cron.EventService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by bhdr on 17.07.2017.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EventServiceTest {

    @Autowired
    private EventService eventService;


    @Test
    public void generateSistem9Events() {
        eventService.generateSistem9Events();
    }



}
