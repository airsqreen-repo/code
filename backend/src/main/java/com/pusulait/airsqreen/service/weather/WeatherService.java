package com.pusulait.airsqreen.service.weather;

import com.pusulait.airsqreen.domain.dto.weather.WeatherDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yildizib on 18/09/2017.
 */
public interface WeatherService {

    /**
     * Cron da kullanilack. Liste verilecek onlarin sicaklik bilgileri
     * cekilecek. koordinat listesi verilecek ve o liste cekilecektir.
     * Bunu mesela saatte bir calisacak bir cron icine atabilirsiniz.
     *
     * @param weatherDTOList
     * @throws NullPointerException
     */
    void loadTempsWithGeoCoordinates(List<WeatherDTO> weatherDTOList) throws NullPointerException;

    /**
     * @param latitude
     * @param longitude
     * @param update
     * @return
     * @throws NullPointerException
     */
    WeatherDTO getTempWithGeoCoordinates(BigDecimal latitude, BigDecimal longitude, boolean update) throws NullPointerException;

    /**
     * @param weatherDTO
     * @param update
     * @return
     * @throws NullPointerException
     */
    WeatherDTO getTempWithGeoCoordinates(WeatherDTO weatherDTO, boolean update) throws NullPointerException;

    void initialize();
}
