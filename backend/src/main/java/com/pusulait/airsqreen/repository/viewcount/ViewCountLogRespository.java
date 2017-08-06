package com.pusulait.airsqreen.repository.viewcount;

import com.pusulait.airsqreen.domain.viewcount.ViewCountLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by yildizib on 03/08/2017.
 */
@Repository
@RepositoryRestResource(exported = false)
public interface ViewCountLogRespository extends JpaRepository<ViewCountLog, Long> {

    /**
     * @param viewCoundId
     * @param start
     * @param end
     * @return
     */
    @Query("SELECT COUNT(u.id) FROM ViewCountLog u WHERE u.viewCountId = :viewCoundId AND (u.createdDate BETWEEN :startDate AND :endDate)")
    Long findByTrackingTokenWithDateRange(@Param("viewCoundId") Long viewCoundId, @Param("startDate") Date start, @Param("endDate") Date end);
}
