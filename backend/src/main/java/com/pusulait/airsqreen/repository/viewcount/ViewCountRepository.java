package com.pusulait.airsqreen.repository.viewcount;

import com.pusulait.airsqreen.domain.viewcount.ViewCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yildizib on 03/08/2017.
 */
@Repository
@RepositoryRestResource(exported = false)
public interface ViewCountRepository extends JpaRepository<ViewCount, Long> {

    /**
     * @param campaignId
     * @param sectionId
     * @return
     */
    ViewCount findByCampaignIdAndCampaignSectionId(String campaignId, String sectionId);

    /**
     * @param trackToken
     * @return
     */
    ViewCount findByTrackingToken(String trackToken);

    /**
     * @param campaignId
     * @return
     */
    List<ViewCount> findByCampaignId(String campaignId);

}
