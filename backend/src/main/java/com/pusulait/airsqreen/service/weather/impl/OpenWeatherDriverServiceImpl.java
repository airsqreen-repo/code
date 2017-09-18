package com.pusulait.airsqreen.service.weather.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
        String url = "{API_URL}/{WEATHER_POSTFIX}?unit=metric&lang=tr&lat={LATITUDE}&lon={LONGITUDE}&appid={API_KEY}"
                .replace("{API_URL}", apiUrl)
                .replace("{WEATHER_POSTFIX}", weatherPostfix)
                .replace("{API_KEY}", apiKey)
                .replace("{LATITUDE}", "41.060459")
                .replace("{LONGITUDE}", "28.987169");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType("application", "json"));

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        ResponseEntity<WeatherResponse> responseEntity = null;

        try {

            responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);
            if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                WeatherResponse res = responseEntity.getBody();
                if (res != null) {
                    result = new WeatherDTO();
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
        @JsonProperty(value = "main")
        private WeatherResMain main;
        @JsonProperty(value = "id")
        private Long id;
        @JsonProperty(value = "name")
        private String name;

    }

    /**
     *
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class WeatherResMain implements Serializable {
        @JsonProperty(value = "temp")
        private Float temp;
        @JsonProperty(value = "temp_min")
        private Float tempMin;
        @JsonProperty(value = "temp_max")
        private Float tempMax;

    }
}
