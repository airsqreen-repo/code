package com.pusulait.airsqreen.repository.view;

import com.pusulait.airsqreen.domain.view.ViewActiveDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ferhatyaban on 24.07.2017.
 */

@Repository
@RepositoryRestResource(exported = false)
@Transactional(readOnly = true)
public interface ViewActiveDeviceRepository extends JpaRepository<ViewActiveDevice, Long> {

    public List<ViewActiveDevice> findAll();

}
