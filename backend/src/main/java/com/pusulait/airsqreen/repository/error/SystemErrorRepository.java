package com.pusulait.airsqreen.repository.error;

import com.pusulait.airsqreen.domain.error.SystemError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 */

@Repository
@RepositoryRestResource(exported = false)
public interface SystemErrorRepository extends JpaRepository<SystemError, Long> {


}
