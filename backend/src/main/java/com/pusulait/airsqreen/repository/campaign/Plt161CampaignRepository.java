package com.pusulait.airsqreen.repository.campaign;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by benan on 7/14/2017.
 */
@Repository
@RepositoryRestResource(exported = false)
public interface Plt161CampaignRepository extends JpaRepository<Plt161Campaign, Long> {


}
