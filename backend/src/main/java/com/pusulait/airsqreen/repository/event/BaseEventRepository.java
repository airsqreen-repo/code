package com.pusulait.airsqreen.repository.event;

import com.pusulait.airsqreen.domain.SystemError;
import com.pusulait.airsqreen.domain.event.BaseEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;


/**
 * Created by benan on 7/16/2017.
 */


@Repository
@RepositoryRestResource(exported = false)
public interface BaseEventRepository extends JpaRepository<BaseEvent, Long> {

}
