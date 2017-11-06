package com.pusulait.airsqreen.domain.dto.report;

import lombok.Data;

@Data
public class SspViewCountDTO {

    private String sspPrice;
    private String date;
    private String deviceName;

    public SspViewCountDTO(String sspPrice, String date, String deviceName) {
        this.sspPrice = sspPrice;
        this.date = date;
        this.deviceName = deviceName;
    }
}
