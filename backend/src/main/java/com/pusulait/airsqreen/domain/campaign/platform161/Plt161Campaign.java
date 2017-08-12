package com.pusulait.airsqreen.domain.campaign.platform161;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.dto.campaign.enums.DeliveryType;
import com.pusulait.airsqreen.domain.dto.campaign.enums.FrequencyCapType;
import com.pusulait.airsqreen.domain.dto.campaign.enums.RtbOptimizeType;
import com.pusulait.airsqreen.domain.integration.PlatformUser;
import com.pusulait.airsqreen.domain.pg_hibernate.LongArrayType;
import com.pusulait.airsqreen.domain.pg_hibernate.StringArrayType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;

//import com.pusulait.airsqreen.domain.pg_hibernate.LongArrayType;

/**
 * Created by benan on 7/14/2017.
 */
@Entity
@Table(name = Constants.PREFIX + "PLATFORM_161_CAMPAIGNS")
@Data
public class Plt161Campaign extends Campaign {

    private Double media_budget;

    private String targeting_weekday_ids;

    private String targeting_hour_ids;

    @Column(name = "UPDATED_AT")
    private Date updated_at;

    @Column(name = "FILTERED_SECTION_IDS")
    private String filtered_section_ids;

    @JoinColumn(name = "PLATFORM_USER_ID", insertable = false, updatable = false)
    @ManyToOne
    private PlatformUser platformUser;

    @Column(name = "PLATFORM_USER_ID")
    private Long platformUserId;

}
