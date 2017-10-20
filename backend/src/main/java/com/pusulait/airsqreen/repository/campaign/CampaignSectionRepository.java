package com.pusulait.airsqreen.repository.campaign;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.CampaignSection;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;
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
public interface CampaignSectionRepository extends JpaRepository<CampaignSection, Long> {

    @Query("select cs from CampaignSection cs where cs.campaign.externalId =:campaignId and cs.section.externalId =:sectionId")
    public CampaignSection findByCampaignAndSectionId(@Param(value = "campaignId") Long campaignId, @Param(value = "sectionId") Long sectionId);


    @Query("select c from CampaignSection c where c.campaignId = :campaignId")
    List<CampaignSection> findByCampaignId(@Param(value = "campaignId") Long campaignId);

}
