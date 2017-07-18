package com.pusulait.airsqreen.service;

import com.pusulait.airsqreen.domain.campaign.InventorySource2;
import com.pusulait.airsqreen.domain.dto.campaign.InventorySourceDTO2;
import com.pusulait.airsqreen.repository.campaign.CampaignRepository;
import com.pusulait.airsqreen.repository.campaign.InventorySource2Repository;
import com.pusulait.airsqreen.service.paltform161.Platform161Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bhdr on 16.07.2017.
 */
@Slf4j
@Service
@Transactional
public class InventoryService {

    @Autowired
    private Platform161Service platform161Service;

    @Autowired
    private InventorySource2Repository inventorySource2Repository;

    @Transactional
    public void saveInventorySources() {

        List<InventorySource2> inventorySourceList = platform161Service.getAllInventories().stream().map(InventorySourceDTO2::toEntity).collect(Collectors.toList());
        for (InventorySource2 inventorySource : inventorySourceList)
            inventorySource2Repository.save(inventorySource);
    }

}

