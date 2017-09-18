package com.pusulait.airsqreen.service.weather.impl;

import com.pusulait.airsqreen.domain.dto.weather.WeatherDTO;
import com.pusulait.airsqreen.service.weather.WeatherDriverService;
import com.pusulait.airsqreen.service.weather.WeatherService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yildizib on 18/09/2017.
 */
@Data
@Slf4j
@Service("weatherService")
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private WeatherDriverService driver;

    /**
     *
     */
    @PostConstruct
    @Override
    public void initialize() {
        driver.initialize();
    }

    /**
     * @param latitude
     * @param longitude
     * @return
     */
    @Override
    public WeatherDTO getTempWithGeoCoordinates(BigDecimal latitude, BigDecimal longitude) throws NullPointerException {
        if (longitude == null || latitude == null) {
            throw new NullPointerException("Geo koordinatlar bos olamaz!");
        }
        return driver.getTempWithGeoCoordinates(latitude, longitude);
    }

    /**
     * @param weatherDTO
     * @return
     */
    @Override
    public WeatherDTO getTempWithGeoCoordinates(WeatherDTO weatherDTO) throws NullPointerException {
        if (weatherDTO == null || weatherDTO.getLatitude() == null || weatherDTO.getLongitude() == null) {
            throw new NullPointerException("Geo koordinatlar bos olamaz!");
        }
        return getTempWithGeoCoordinates(weatherDTO.getLatitude(), weatherDTO.getLongitude());
    }

    /**
     * @param weatherDTOList
     */
    @Override
    public void loadTempsWithGeoCoordinates(List<WeatherDTO> weatherDTOList) throws NullPointerException {
        if (weatherDTOList != null && weatherDTOList.size() > 0) {
            for (WeatherDTO dto : weatherDTOList) {
                getTempWithGeoCoordinates(dto);
            }
        }
    }
}
