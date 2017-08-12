package com.pusulait.airsqreen.repository.plt161;


import com.pusulait.airsqreen.domain.event.BaseEvent;
import com.pusulait.airsqreen.domain.integration.PlatformUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
public interface PlatformUserRepository extends JpaRepository<PlatformUser, Long> {

}


