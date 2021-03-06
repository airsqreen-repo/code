package com.pusulait.airsqreen.service.weather.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;
import com.pusulait.airsqreen.domain.dto.weather.WeatherDTO;
import com.pusulait.airsqreen.service.weather.WeatherDriverService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yildizib on 18/09/2017.
 */
@Data
@Slf4j
@Service("weatherDriverService")
public class OpenWeatherDriverServiceImpl implements WeatherDriverService {

    private RestTemplate restTemplate;
    @Value("${weather.openweather.api_url}")
    private String apiUrl;
    @Value("${weather.openweather.api_key}")
    private String apiKey;
    @Value("${weather.openweather.weather_postfix}")
    private String weatherPostfix;

    /**
     *
     */
    @Override
    public void initialize() {
        log.debug("*** -> OpenWeatherDriverServiceImpl servisi weatherDriverService olarak inject edildi.");
    }

    /**
     * @param latitude
     * @param longitude
     * @return
     * @throws NullPointerException
     */
    @Override
    public WeatherDTO getTempWithGeoCoordinates(BigDecimal latitude, BigDecimal longitude) throws NullPointerException {

        if (latitude == null || longitude == null) {
            throw new NullPointerException("Weather service latitude and longitude is empty!");
        }

        WeatherDTO result = null;
        String url = "{API_URL}/{WEATHER_POSTFIX}?units=metric&lang=tr&lat={LATITUDE}&lon={LONGITUDE}&appid={API_KEY}"
                .replace("{API_URL}", apiUrl)
                .replace("{WEATHER_POSTFIX}", weatherPostfix)
                .replace("{API_KEY}", apiKey)
                .replace("{LATITUDE}", latitude.setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString())
                .replace("{LONGITUDE}", longitude.setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType("application", "json"));

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        ResponseEntity<String> responseEntity = null;

        try {

            responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                String res = responseEntity.getBody();
                Gson gson = new Gson();
                WeatherResponse response = gson.fromJson(res, WeatherResponse.class);
                if (response != null) {
                    result = new WeatherDTO();

                    result.setLatitude(latitude);
                    result.setLongitude(longitude);

                    if (response.getMain() == null) {
                        throw new NullPointerException("Donen sicaklik bilgileri NULL olamaz! Kontrol edin!");
                    }

                    result.setTemp(response.getMain().getTemp());
                    result.setTempMin(response.getMain().getTempMin());
                    result.setTempMax(response.getMain().getTempMax());
                    result.setHumidity(response.getMain().getHumidity());
                    result.setPressure(response.getMain().getPressure());
                    result.setWindDeg(response.getWind().getDeg());
                    result.setSpeed(response.getWind().getSpeed());

                    if(response.getWeatherMainList() != null && response.getWeatherMainList().size() > 0) {
                        result.setMain(response.getWeatherMainList().get(0).getMain());
                    }
                }
            }

        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }

    /**
     *
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    class WeatherResponse implements Serializable {
        @SerializedName(value = "main")
        private WeatherResMain main;
        @SerializedName(value = "id")
        private Long id;
        @SerializedName(value = "name")
        private String name;
        @SerializedName(value = "wind")
        private WeatherWind wind;
        @SerializedName(value = "clouds")
        private WeatherClouds clouds;
        @SerializedName(value = "weather")
        private List<WeatherMain> weatherMainList;


    }


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class WeatherClouds implements Serializable {

        @SerializedName(value = "all")
        private Float all;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class WeatherWind implements Serializable {

        @SerializedName(value = "speed")
        private Float speed;
        @SerializedName(value = "deg")
        private Float deg;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class WeatherMain implements Serializable {

        @SerializedName(value = "main")
        private String main;

        @SerializedName(value = "id")
         private Long id;

        @SerializedName(value = "description")
        private String description;
    }

    /**
     *
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class WeatherResMain implements Serializable {
        @SerializedName(value = "temp")
        private Float temp;
        @SerializedName(value = "temp_min")
        private Float tempMin;
        @SerializedName(value = "temp_max")
        private Float tempMax;
        @SerializedName(value = "humidity")
        private Float humidity;
        @SerializedName(value = "pressure")
        private Float pressure;

    }
}
