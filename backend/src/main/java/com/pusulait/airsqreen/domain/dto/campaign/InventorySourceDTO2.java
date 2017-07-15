package com.pusulait.airsqreen.domain.dto.campaign;

import lombok.Data;

import java.util.List;

/**
 * Created by bhdr on 15.07.2017.
 *
 * Documentation: GET v2/inventory_sources
 */
@Data
public class InventorySourceDTO2 {

    private Long id;
    private String name;
    private String code;

    private List<InventoryDTO> inventory;

}
