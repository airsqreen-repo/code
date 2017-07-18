package service;

import com.pusulait.airsqreen.Application;
import com.pusulait.airsqreen.service.CampaignService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by bhdr on 17.07.2017.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CampaignServiceIntegrationTest {

    @Autowired
    private CampaignService campaignService;

    @Test
    public void savePlt161Campaigns() {
        campaignService.savePlt161(5);
    }

    @Test
    public void updatePlt161Campaigns() {
        // setup db
        campaignService.savePlt161(5);

        campaignService.updateCampaigns();

    }


}
