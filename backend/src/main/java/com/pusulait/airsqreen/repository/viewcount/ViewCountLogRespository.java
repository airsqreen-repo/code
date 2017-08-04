package com.pusulait.airsqreen.repository.viewcount;

import com.pusulait.airsqreen.domain.viewcount.ViewCountLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by yildizib on 03/08/2017.
 */
@Repository
@RestResource(exported = false)
public interface ViewCountLogRespository extends JpaRepository<ViewCountLog, Long> {
}
