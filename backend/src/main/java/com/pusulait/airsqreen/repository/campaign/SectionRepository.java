package com.pusulait.airsqreen.repository.campaign;

import com.pusulait.airsqreen.domain.base.DataStatus;
import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.Publisher;
import com.pusulait.airsqreen.domain.campaign.Section;
import com.pusulait.airsqreen.domain.campaign.sistem9.Device;
import com.pusulait.airsqreen.domain.integration.PlatformUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    Page<Section> findByNameContainingIgnoreCaseAndDataStatus(String name, DataStatus dataStatus, Pageable pageable);

    Page<Section> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Section> findByDataStatus(DataStatus dataStatus, Pageable pageable);



}
