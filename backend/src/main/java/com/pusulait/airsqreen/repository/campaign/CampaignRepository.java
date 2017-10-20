package com.pusulait.airsqreen.repository.campaign;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by benan on 7/13/2017.
 */
@Repository
@RepositoryRestResource(exported = false)
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    @Query("select c from Campaign c where c.externalId = :id")
    Optional<Plt161Campaign> findByExternalId(@Param(value = "id") Long id);

    @Query("select c from Campaign c where c.dataStatus = 'ACTIVE' ")
    List<Campaign> findAllActiveCampaigns();

    @Query("select c from Campaign c where now() between c.startOn and c.endOn and c.dataStatus = 'ACTIVE'")
    List<Plt161Campaign> findLiveCampaigns();



    Page<Campaign> findByActive(Boolean active, Pageable pageable);


}
