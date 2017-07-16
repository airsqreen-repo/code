package service;

import com.pusulait.airsqreen.Application;
import com.pusulait.airsqreen.domain.dto.campaign.CampaignDTO;
import com.pusulait.airsqreen.domain.dto.campaign.InventorySourceDTO2;
import com.pusulait.airsqreen.domain.dto.publisher.PublisherDTO;
import com.pusulait.airsqreen.domain.dto.section.SectionDTO;
import com.pusulait.airsqreen.service.CampaignService;
import com.pusulait.airsqreen.service.InventoryService;
import com.pusulait.airsqreen.service.paltform161.Platform161Service;
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
 * Created by benan on 5/14/2017.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TestPaltform161 {


    @Autowired
    private Platform161Service platform161Service;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private InventoryService inventoryService;

    @Test
    public void getToken() {

        String token = platform161Service.getAuthToken();
        log.debug("token:" + token);
    }

    @Test
    public void getCampaigns() {
        String token = platform161Service.getAuthToken();
        log.debug("token:" + token);
        List<CampaignDTO> list = platform161Service.getCampaign(token);
        log.debug("list:" + list.size());

    }

    @Test
    public void savePlt161Campaigns() {
        campaignService.savePlt161();
    }

    @Test
    public void getInventorySources() {
        String token = platform161Service.getAuthToken();
        log.debug("token:" + token);
        List<InventorySourceDTO2> list = platform161Service.getInventory(token);
        log.debug("list:" + list.size());

    }

    @Test
    public void saveInventorySources() {
        inventoryService.saveInventorySources();
    }


    @Test
    public void getSections() {
        String token = platform161Service.getAuthToken();
        log.debug("token:" + token);
        List<SectionDTO> list = platform161Service.getSections(token);
        log.debug("list:" + list.size());

    }

    @Test
    public void getSection() {
        String token = platform161Service.getAuthToken();
        log.debug("token:" + token);
        SectionDTO sectionDTO = platform161Service.getSection(token,3027210L);
        log.debug("sectionDTO:" + sectionDTO);

    }


    @Test
    public void getPublishers() {
        String token = platform161Service.getAuthToken();
        log.debug("token:" + token);
        List<PublisherDTO> list = platform161Service.getPublishers(token);
        log.debug("list:" + list.size());

    }





}

