package com.pusulait.airsqreen.domain.dto.report;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
public class EventRunReportDTO {

    public BigInteger campaignId;
    public BigInteger count;
    public String date;
    public Double hour;
    public String campaignName;

    public EventRunReportDTO(BigInteger campaignId, BigInteger count, String date, String campaignName) {
        this.campaignId = campaignId;
        this.count = count;
        this.date = date;
        this.campaignName = campaignName;
    }

    public EventRunReportDTO(BigInteger campaignId, BigInteger count, Double hour, String campaignName) {
        this.campaignId = campaignId;
        this.count = count;
        this.hour = hour;
        this.campaignName = campaignName;
    }

}
