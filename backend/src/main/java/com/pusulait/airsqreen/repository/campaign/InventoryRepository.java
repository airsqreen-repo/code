package com.pusulait.airsqreen.repository.campaign;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by benan on 7/13/2017.
 */
@Repository
@RepositoryRestResource(exported = false)
public interface InventoryRepository extends JpaRepository<Inventory, Long> {


}
