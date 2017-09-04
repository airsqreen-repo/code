package com.pusulait.airsqreen.repository.device;

import com.pusulait.airsqreen.domain.campaign.sistem9.Device;
import com.pusulait.airsqreen.domain.campaign.sistem9.DeviceConstraint;
import com.pusulait.airsqreen.domain.security.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by benan on 7/13/2017.
 */
@Repository
@RepositoryRestResource(exported = false)
public interface DeviceConstraintRepository extends JpaRepository<DeviceConstraint, Long> {

    public List<DeviceConstraint> findByDeviceId(Long deviceId);
}
