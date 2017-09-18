package com.pusulait.airsqreen.service.weather;

import com.pusulait.airsqreen.domain.dto.weather.WeatherDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yildizib on 18/09/2017.
 */
@Slf4j
@Data
@Service
public class WeatherCronService {

    @Autowired
    private WeatherService weatherService;

    /**
     * Verilen location listesine gore sicaklik bilgileri guncellenir.
     * Eger hic data yok ise eklenir.
     * <p>
     * Bu location bilgileri Device bilgilerinden alinacaktir.
     * Gerekli yerler doldurulunca servis calisir hale gelecektir.
     */
    @Scheduled(fixedDelay = 5 * 1000L)
    public void updateTempListCron() {
        log.debug(" - updateTempListCron() basladi.");

        /*
        * Burada bu liste device koordinatlari ile dolacak
        * ardindan bu cron bu bilgileri update edecek saat basi.
        * diger fonksiyonlar ile de sorgulanacak.
        *
        * */
        List<WeatherDTO> updateList = new LinkedList<>();

        WeatherDTO ornekDTO = new WeatherDTO();
        ornekDTO.setLatitude(new BigDecimal(28.60));
        ornekDTO.setLongitude(new BigDecimal(41.90));

        updateList.add(ornekDTO);

        /* Guncelleme burada olacak */
        weatherService.loadTempsWithGeoCoordinates(updateList);

        log.debug(" - updateTempListCron() bitti.");
    }
}
