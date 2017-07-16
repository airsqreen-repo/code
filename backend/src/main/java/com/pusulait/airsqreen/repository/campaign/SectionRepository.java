package com.pusulait.airsqreen.repository.campaign;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by bhdr on 16.07.2017.
 */
@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
}
