package com.pusulait.airsqreen.repository.campaign;

import com.pusulait.airsqreen.domain.campaign.Publisher;
import com.pusulait.airsqreen.domain.campaign.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by bhdr on 17.07.2017.
 */
@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
