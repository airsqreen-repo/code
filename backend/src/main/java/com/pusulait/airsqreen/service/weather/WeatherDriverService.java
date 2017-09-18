package com.pusulait.airsqreen.service.weather;

import com.pusulait.airsqreen.domain.dto.weather.WeatherDTO;

import java.math.BigDecimal;

/**
 * Created by yildizib on 18/09/2017.
 */
public interface WeatherDriverService {

    /**
     * @param latitude
     * @param longitude
     * @return
     * @throws NullPointerException
     */
    WeatherDTO getTempWithGeoCoordinates(BigDecimal latitude, BigDecimal longitude) throws NullPointerException;

    /**
     *
     */
    void initialize();

}
