package com.pusulait.airsqreen.repository.plt161;


import com.pusulait.airsqreen.domain.enums.PlatformType;
import com.pusulait.airsqreen.domain.enums.ServiceType;
import com.pusulait.airsqreen.domain.event.BaseEvent;
import com.pusulait.airsqreen.domain.integration.PlatformUser;
import com.pusulait.airsqreen.domain.viewcount.ViewCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(exported = false)
public interface PlatformUserRepository extends JpaRepository<PlatformUser, Long> {


    List<PlatformUser> findByPlatformTypeAndServiceType(PlatformType platformType, ServiceType serviceType);


}


