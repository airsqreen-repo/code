package com.pusulait.airsqreen.repository.view;

import com.pusulait.airsqreen.domain.view.SspViewCount;
import com.pusulait.airsqreen.domain.view.SspViewCountLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@RepositoryRestResource(exported = false)
@Transactional(readOnly = true)
public interface SspViewCountLogRepository extends JpaRepository<SspViewCountLog, Long> {

    public List<SspViewCountLog> findAll();


}
