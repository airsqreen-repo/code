package com.pusulait.airsqreen.repository.campaign;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.Publisher;
import com.pusulait.airsqreen.domain.campaign.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by bhdr on 16.07.2017.
 */
@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query("select s from Section s where s.externalId = :id")
    Optional<Section> findByExternalId(@Param(value = "id") Long id);

}
