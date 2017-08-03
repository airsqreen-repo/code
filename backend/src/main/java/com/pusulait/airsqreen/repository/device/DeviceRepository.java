package com.pusulait.airsqreen.repository.device;

import com.pusulait.airsqreen.domain.campaign.sistem9.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by benan on 7/13/2017.
 */
@Repository
@RepositoryRestResource(exported = false)
public interface DeviceRepository extends JpaRepository<Device, Long> {


}
