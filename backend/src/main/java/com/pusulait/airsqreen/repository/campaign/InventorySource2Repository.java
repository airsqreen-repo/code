package com.pusulait.airsqreen.repository.campaign;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.InventorySource2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by bhdr on 16.07.2017.
 */
@Repository
public interface InventorySource2Repository extends JpaRepository<InventorySource2, Long> {
}
