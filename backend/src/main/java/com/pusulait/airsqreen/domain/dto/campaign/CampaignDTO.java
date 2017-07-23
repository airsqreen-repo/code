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

    private String name;

    private Boolean active;

    private PricingType pricingType;

    private String target;
    private Date startOn;

    private Date endOn;

    private Double price;

    private Double agencyFee;

}
