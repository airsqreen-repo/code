package com.pusulait.airsqreen.domain.dto.campaign;

import com.pusulait.airsqreen.domain.dto.campaign.enums.PricingType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by benan on 7/23/2017.
 */
@Data
public class CampaignDTO {

    private Long id;

    private Long externalId;

    private String uniqueCampaignUId;

    private String name;

    private Boolean active;

    private String pricing_type;

    private PricingType pricingType;

    private String target;
    private Date start_on;

    private Date end_on;

    private Double price;

    private Double agencyFee;

}
