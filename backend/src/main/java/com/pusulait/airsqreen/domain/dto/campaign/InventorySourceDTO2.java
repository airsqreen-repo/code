package com.pusulait.airsqreen.domain.dto.campaign;

import com.pusulait.airsqreen.domain.campaign.InventorySource2;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;
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

    public static InventorySource2 toEntity(InventorySourceDTO2 inventorySourceDTO) {

        InventorySource2 inventorySource2 = new InventorySource2();
        inventorySource2.setName(inventorySourceDTO.getName());
        inventorySource2.setCode(inventorySourceDTO.getCode());

        return inventorySource2;
    }

}
