package com.pusulait.airsqreen.repository.plt161;


import com.pusulait.airsqreen.domain.base.DataStatus;
import com.pusulait.airsqreen.domain.campaign.Section;
import com.pusulait.airsqreen.domain.enums.PlatformType;
import com.pusulait.airsqreen.domain.enums.ServiceType;
import com.pusulait.airsqreen.domain.event.BaseEvent;
import com.pusulait.airsqreen.domain.integration.PlatformUser;
import com.pusulait.airsqreen.domain.viewcount.ViewCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RepositoryRestResource(exported = false)
public interface PlatformUserRepository extends JpaRepository<PlatformUser, Long> {


    List<PlatformUser> findByPlatformTypeAndServiceType(PlatformType platformType, ServiceType serviceType);

    @Query("select pu from PlatformUser pu where pu.platformType = :platformType and pu.serviceType = :serviceType")
    Optional<PlatformUser> findOnePlatformTypeAndServiceType(@Param(value = "platformType") PlatformType platformType, @Param(value = "serviceType") ServiceType serviceType);



    Page<PlatformUser> findAll(Pageable page);

    Page<PlatformUser> findByUsernameContainingIgnoreCaseAndDataStatus(String username, DataStatus dataStatus, Pageable pageable);

    Page<PlatformUser> findByUsernameContainingIgnoreCase(String username, Pageable pageable);

    Page<PlatformUser> findByDataStatus(DataStatus dataStatus, Pageable pageable);


}


