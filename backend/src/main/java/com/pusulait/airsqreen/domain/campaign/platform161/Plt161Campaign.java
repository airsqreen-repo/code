package com.pusulait.airsqreen.domain.campaign.platform161;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.dto.campaign.enums.DeliveryType;
import com.pusulait.airsqreen.domain.dto.campaign.enums.FrequencyCapType;
import com.pusulait.airsqreen.domain.dto.campaign.enums.RtbOptimizeType;
import com.pusulait.airsqreen.domain.pg_hibernate.LongArrayType;
import com.pusulait.airsqreen.domain.pg_hibernate.StringArrayType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

//import com.pusulait.airsqreen.domain.pg_hibernate.LongArrayType;

/**
 * Created by benan on 7/14/2017.
 */
@Entity
@Table(name = Constants.PREFIX + "PLATFORM_161_CAMPAIGNS")
@TypeDefs(value = {
        @TypeDef(name = "text", typeClass = StringArrayType.class)
       , @TypeDef(name = "longarray", typeClass = LongArrayType.class)
})
@Data
public class Plt161Campaign extends Campaign {

  private Double media_budget;

    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] targeting_weekday_ids;

    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] targeting_hour_ids;

    private Date updated_at;

    @Type(type = "longarray")
    @Column(columnDefinition = "text")
    private Long[] filtered_section_ids;

}
