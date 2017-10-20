package com.pusulait.airsqreen.repository.campaign;

import com.pusulait.airsqreen.domain.campaign.CampaignConstraint;
import com.pusulait.airsqreen.domain.campaign.CampaignSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by benan on 7/13/2017.
 */
@Repository
@RepositoryRestResource(exported = false)
public interface CampaignConstraintRepository extends JpaRepository<CampaignConstraint, Long> {



    @Query("select c from CampaignConstraint c where c.campaignId = :campaignId")
    List<CampaignConstraint> findByCampaignId(@Param(value = "campaignId") Long campaignId);

}
