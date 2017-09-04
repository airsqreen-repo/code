package com.pusulait.airsqreen.repository.device;

import com.pusulait.airsqreen.domain.base.DataStatus;
import com.pusulait.airsqreen.domain.campaign.sistem9.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by ferhatyaban on 8/31/2017.
 */
@Repository
@RepositoryRestResource(exported = false)
public interface DeviceRepository extends JpaRepository<Device, Long> {



    Page<Device> findAll(Pageable page);

    Page<Device> findByNameContainingIgnoreCaseAndDataStatus(String name, DataStatus dataStatus, Pageable pageable);

    Page<Device> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Device> findByDataStatus(DataStatus dataStatus, Pageable pageable);


}

