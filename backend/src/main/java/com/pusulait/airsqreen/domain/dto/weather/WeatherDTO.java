package com.pusulait.airsqreen.domain.dto.weather;

import com.pusulait.airsqreen.domain.weather.Weather;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Created by yildizib on 03/08/2017.
 */
@Data
public class WeatherDTO {
    @Digits(integer = 2, fraction = 6)
    private BigDecimal latitude;
    @Digits(integer = 2, fraction = 6)
    private BigDecimal longitude;
    @Size(min = -40, max = 80)
    private Float temp;
    @Size(min = -40, max = 80)
    private Float tempMin;
    @Size(min = -40, max = 80)
    private Float tempMax;

    /**
     * @return
     */
    public Weather getEntity() {
        Weather result = new Weather();

        result.setLatitude(getLatitude());
        result.setLongitude(getLongitude());
        result.setTemp(getTemp());
        result.setTempMin(getTempMin());
        result.setTempMax(getTempMax());

        return result;
    }
}
