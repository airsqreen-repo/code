package com.pusulait.airsqreen.domain.dto.report;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
public class EventRunReportDTO {

    public BigInteger campaignId;
    public BigInteger count;
    public String date;

    public EventRunReportDTO(BigInteger campaignId, BigInteger count, String date) {
        this.campaignId = campaignId;
        this.count = count;
        this.date = date;
    }
}
