package com.pusulait.airsqreen.service.weather.impl;

import com.pusulait.airsqreen.domain.dto.weather.WeatherDTO;
import com.pusulait.airsqreen.domain.weather.Weather;
import com.pusulait.airsqreen.repository.weather.WeatherRepository;
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

    @Autowired
    private WeatherRepository repository;

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
    public WeatherDTO getTempWithGeoCoordinates(BigDecimal latitude, BigDecimal longitude, boolean update) throws NullPointerException {
        if (longitude == null || latitude == null) {
            throw new NullPointerException("Geo koordinatlar bos olamaz!");
        }

        WeatherDTO result = null;
        Weather entity = repository.findByLatitudeAndLongitude(latitude, longitude);
        if (entity == null) {

            result = driver.getTempWithGeoCoordinates(latitude, longitude);

            if (result == null) {
                throw new NullPointerException("Weather service respnse is empty!");
            }

            entity = result.getEntity();
            repository.save(entity);

        } else if (update) {

            result = driver.getTempWithGeoCoordinates(latitude, longitude);

            if (result == null) {
                throw new NullPointerException("Weather service respnse is empty!");
            }

            entity.setTemp(result.getTemp());
            entity.setTempMin(result.getTempMin());
            entity.setTempMax(result.getTempMax());

            repository.save(entity);

        } else if (!update && entity != null) {

            result = new WeatherDTO();

            result.setTempMax(entity.getTempMax());
            result.setTemp(entity.getTemp());
            result.setTempMin(entity.getTempMin());
            result.setLatitude(entity.getLatitude());
            result.setLongitude(entity.getLongitude());

        }

        return result;
    }

    /**
     *
     * @param weatherDTO
     * @param update
     * @return
     * @throws NullPointerException
     */
    @Override
    public WeatherDTO getTempWithGeoCoordinates(WeatherDTO weatherDTO, boolean update) throws NullPointerException {
        if (weatherDTO == null || weatherDTO.getLatitude() == null || weatherDTO.getLongitude() == null) {
            throw new NullPointerException("Geo koordinatlar bos olamaz!");
        }
        return getTempWithGeoCoordinates(weatherDTO.getLatitude(), weatherDTO.getLongitude(), update);
    }

    /**
     * @param weatherDTOList
     */
    @Override
    public void loadTempsWithGeoCoordinates(List<WeatherDTO> weatherDTOList) throws NullPointerException {
        if (weatherDTOList != null && weatherDTOList.size() > 0) {
            for (WeatherDTO dto : weatherDTOList) {
                getTempWithGeoCoordinates(dto, true);
            }
        }
    }
}
