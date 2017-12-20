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

        //59.911491, 10.757933.
        //(57.1649118,65.5198002)
        WeatherDTO dto = weatherService.getTempWithGeoCoordinates(new BigDecimal(57.1649118), new BigDecimal(65.5198002), true);
        log.debug(dto.toString());
    }
}
