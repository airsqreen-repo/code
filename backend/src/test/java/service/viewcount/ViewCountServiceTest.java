package service.viewcount;

import com.pusulait.airsqreen.Application;
import com.pusulait.airsqreen.service.viewcount.ViewCountAndPriceService;
import com.pusulait.airsqreen.service.viewcount.ViewCountService;
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
public class ViewCountServiceTest {

    @Autowired
    private ViewCountService viewCountService;
    @Autowired
    private ViewCountAndPriceService viewCountAndPriceService;

    @Test
    public void test() {
        //viewCountService.save("dfasdf", "dfasdf", "sfdasdf", "sadfasf", null);
        //String token = viewCountService.getTrackToken("dfasdf", "sadfasf");
        //for (int i = 0; i < 100; i++) {
        //    viewCountService.incrementViewCount(token);
        //}
        viewCountAndPriceService.getTotalSpent("dfsdfdsaf");
    }

}
