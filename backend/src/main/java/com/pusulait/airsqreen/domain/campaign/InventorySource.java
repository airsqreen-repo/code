package com.pusulait.airsqreen.domain.campaign;

import lombok.Data;

import java.util.List;

/**
 * Created by benan on 7/9/2017.
 */
@Data
public class InventorySource {

    private Long id;
    private Double price;
    private Double budget;
    private List<Long> inventory;
    private String domain_filter;
    private List<Long> domains;
}
