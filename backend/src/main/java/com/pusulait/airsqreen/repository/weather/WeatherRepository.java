package com.pusulait.airsqreen.repository.weather;

import com.pusulait.airsqreen.domain.weather.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * Created by yildizib on 03/08/2017.
 */
@Repository
@RepositoryRestResource(exported = false)
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    /**
     * @param latitude
     * @param longitude
     * @return
     */
    Weather findByLatitudeAndLongitude(BigDecimal latitude, BigDecimal longitude);

}
