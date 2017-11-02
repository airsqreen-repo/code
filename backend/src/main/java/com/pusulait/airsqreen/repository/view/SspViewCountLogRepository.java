package com.pusulait.airsqreen.repository.view;

import com.pusulait.airsqreen.domain.view.SspViewCount;
import com.pusulait.airsqreen.domain.view.SspViewCountLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@RepositoryRestResource(exported = false)
@Transactional(readOnly = true)
public interface SspViewCountLogRepository extends JpaRepository<SspViewCountLog, Long> {

    public List<SspViewCountLog> findAll();

    @Query("select svcl from SspViewCountLog svcl where  equalityNull(svcl.sspPrice ,:sspPrice) is true and equalityNull(svcl.deviceId ,:deviceId) is true and equalityNull(svcl.actionId,:actionId) is true and equalityNull(svcl.sspDeviceId,:sspDeviceId) is true and equalityNull(svcl.platformUserId,:platformUserId) is true")
    public List<SspViewCountLog> search(@Param(value = "sspPrice") String sspPrice,@Param(value = "deviceId")String deviceId,@Param(value = "actionId")String actionId,@Param(value = "sspDeviceId")String sspDeviceId,@Param(value = "platformUserId")String platformUserId);


}
