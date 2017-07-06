package com.pusulait.airsqreen.repository.security.user;

import com.pusulait.airsqreen.domain.base.DataStatus;
import com.pusulait.airsqreen.domain.security.user.Right;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by TRB on 09.06.2017.
 */
@Repository
@RepositoryRestResource(exported = false)
public interface RightRepository extends JpaRepository<Right,Long> {
    Right findByName(String name);

    Page<Right> findByNameContainingIgnoreCaseAndDataStatus(String name, DataStatus dataStatus, Pageable pageable);
    Page<Right> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Right> findByDataStatus(DataStatus dataStatus, Pageable pageable);

}