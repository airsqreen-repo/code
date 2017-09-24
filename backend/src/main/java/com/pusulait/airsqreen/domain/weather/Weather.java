package com.pusulait.airsqreen.domain.weather;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.config.constants.Constraints;
import com.pusulait.airsqreen.domain.base.AuditBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by yildizib on 03/08/2017.
 */
@Slf4j
@SQLDelete(sql = "update " + Constants.PREFIX + "WEATHER SET DATA_STATUS = 'DELETED' WHERE id = ? AND version = ?")
@Data
@Entity
@Table(name = Constants.PREFIX + "WEATHER", indexes = {
        @Index(name = "index_weather_general", columnList = "LATITUDE, LONGITUDE"),
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"LATITUDE", "LONGITUDE"}, name = Constraints.UC_WEATHER_GNRL_UQC),
})
@Where(clause = "DATA_STATUS <> 'DELETED'")
@ToString
@EqualsAndHashCode
public class Weather extends AuditBase {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WEATHER_ID")
    @SequenceGenerator(name = "SEQ_WEATHER_ID", sequenceName = "SEQ_WEATHER_ID", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "LATITUDE", nullable = false, scale = 2, precision = 6)
    private BigDecimal latitude;

    @Column(name = "LONGITUDE", nullable = false, scale = 2, precision = 6)
    private BigDecimal longitude;

    @Column(name = "TEMP", scale = 2, precision = 2)
    private Float temp;

    @Column(name = "TEMP_MIN", scale = 2, precision = 2)
    private Float tempMin;

    @Column(name = "TEMP_MAX", scale = 2, precision = 2)
    private Float tempMax;


    /**
     *
     */
    public Weather() {
    }

    /**
     * @param latitude
     * @param longitude
     */
    public Weather(BigDecimal latitude, BigDecimal longitude) {

        this.latitude = latitude;
        this.longitude = longitude;
    }
}
