package service;

import com.pusulait.airsqreen.Application;
import com.pusulait.airsqreen.domain.dto.weather.WeatherDTO;
import com.pusulait.airsqreen.service.weather.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;

/**
 * Created by bhdr on 17.07.2017.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class WeatherServiceIntTest {

    @Autowired
    private WeatherService weatherService;

    @Test
    public void openWeatherApiCallTest() {
        WeatherDTO dto = weatherService.getTempWithGeoCoordinates(new BigDecimal(33), new BigDecimal(33), true);
        log.debug(dto.toString());
    }
}
