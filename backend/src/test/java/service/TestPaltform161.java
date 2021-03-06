package service;

import com.pusulait.airsqreen.Application;
import com.pusulait.airsqreen.service.SectionService;
import com.pusulait.airsqreen.service.paltform161.Platform161Service;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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
    private SectionService sectionService;

   /* @Test
    public void getToken() {

        String token = platform161Service.getAuthToken();
        log.debug("token:" + token);
    }

    @Test
    public void getCampaigns() {
        String token = platform161Service.getAuthToken();
        log.debug("token:" + token);
        List<Plt161CampaignDTO> list = platform161Service.getCampaignList(token);
        log.debug("list:" + list.size());

    }

    @Test
    public void getSections() {
        String token = platform161Service.getAuthToken();
        log.debug("token:" + token);
        List<Plt161SectionDTO> list = platform161Service.getSections(token);
        log.debug("list:" + list.size());

    }

    @Test
    public void saveSections() {
        sectionService.saveSections();
    }*/

  /*  @Test
    public void getSection() {
        String token = platform161Service.getAuthToken();
        log.debug("token:" + token);
        Plt161SectionDTO sectionDTO = platform161Service.getSection(token,3027210L);
        log.debug("sectionDTO:" + sectionDTO);

    }
*/






}

