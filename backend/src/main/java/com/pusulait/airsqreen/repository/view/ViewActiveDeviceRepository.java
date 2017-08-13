package com.pusulait.airsqreen.repository.view;

import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;
import com.pusulait.airsqreen.domain.view.ViewActiveDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by ferhatyaban on 24.07.2017.
 */

@Repository
@RepositoryRestResource(exported = false)
@Transactional(readOnly = true)
public interface ViewActiveDeviceRepository extends JpaRepository<ViewActiveDevice, Long> {

    public List<ViewActiveDevice> findAll();

    @Query("select vad from ViewActiveDevice vad where vad.deviceId = :deviceId")
    Optional<ViewActiveDevice> findByDeviceId(@Param(value = "deviceId") Long deviceId);

}
