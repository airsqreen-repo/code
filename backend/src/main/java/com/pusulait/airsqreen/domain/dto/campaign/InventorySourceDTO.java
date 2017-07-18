package com.pusulait.airsqreen.domain.dto.campaign;

import lombok.Data;

import java.util.List;

/**
 * Created by benan on 7/9/2017.
 *
 * Documentation/doc/app-api/Api/V2/Campaigns.html
 *
 */
@Data
public class InventorySourceDTO {

    private Long id;
    private Double price;
    private Double budget;
    private List<Long> inventory;
    private String domain_filter;
    private List<Long> domains;

}
