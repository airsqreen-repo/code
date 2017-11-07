package com.pusulait.airsqreen.domain.dto.report;

import lombok.Data;

@Data
public class SspViewCountDTO {

    private Double sspPrice;
    private String date;
    private String deviceName;

    public SspViewCountDTO(Double sspPrice, String date, String deviceName) {
        this.sspPrice = sspPrice;
        this.date = date;
        this.deviceName = deviceName;
    }
}
