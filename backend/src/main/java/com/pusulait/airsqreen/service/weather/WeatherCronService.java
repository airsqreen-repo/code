package com.pusulait.airsqreen.service.weather;

import com.pusulait.airsqreen.domain.campaign.sistem9.Device;
import com.pusulait.airsqreen.domain.dto.device.DeviceDTO;
import com.pusulait.airsqreen.domain.dto.weather.WeatherDTO;
import com.pusulait.airsqreen.service.device.DeviceService;
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

    @Autowired
    private DeviceService deviceService;

    /**
     * Verilen location listesine gore sicaklik bilgileri guncellenir.
     * Eger hic data yok ise eklenir.
     * <p>
     * Bu location bilgileri Device bilgilerinden alinacaktir.
     * Gerekli yerler doldurulunca servis calisir hale gelecektir.
     */
    @Scheduled(fixedDelay = 1000L * 60 * 60)
    public void updateTempListCron() {
        log.debug(" - updateTempListCron() basladi.");

        /*
        * Burada bu liste device koordinatlari ile dolacak
        * ardindan bu cron bu bilgileri update edecek saat basi.
        * diger fonksiyonlar ile de sorgulanacak.
        *
        * */
        List<WeatherDTO> updateList = new LinkedList<>();

        for (DeviceDTO device : deviceService.findAll()) {

            WeatherDTO ornekDTO = new WeatherDTO();
            ornekDTO.setLatitude(device.getLatitude());
            ornekDTO.setLongitude(device.getLongitude());
            updateList.add(ornekDTO);
        }

        /* Guncelleme burada olacak */
        weatherService.loadTempsWithGeoCoordinates(updateList);

        log.debug(" - updateTempListCron() bitti.");
    }
}
