package com.pusulait.airsqreen.domain.dto.viewcount;

import lombok.Data;

import java.util.Date;

/**
 * Created by yildizib on 03/08/2017.
 */
@Data
public class ViewCountDTO {
    private String campaignId;
    private String campaignSectionId;
    private String deviceId;
    private String actionId;
    private Long totalCount;
    private Double totalSpend;
    private Date startDate;
    private Date endDate;
    /* ... */
    private Double unitPrice;
}
