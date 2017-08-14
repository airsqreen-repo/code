package com.pusulait.airsqreen.repository.event;

import com.pusulait.airsqreen.domain.event.Sistem9PushEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by benan on 7/16/2017.
 */

@Repository
@RepositoryRestResource(exported = false)
public interface Sistem9PushEventRepository extends JpaRepository<Sistem9PushEvent, Long> {


    //TODO saatte 1 expire date geçmişleri tablodan silmek lazım
    @Query("select wpe from Sistem9PushEvent wpe where wpe.eventStatus = 'WAITING' and wpe.runDate < now() and wpe.expireDate > now() ")
    List<Sistem9PushEvent> findWaitingEvents();

   /* @Query("select wpe from Sistem9PushEvent wpe where wpe.eventStatus = 'WAITING'")
    List<Sistem9PushEvent> findWaitingEvents();
*/
}
