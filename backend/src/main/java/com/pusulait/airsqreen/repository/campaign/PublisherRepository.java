package com.pusulait.airsqreen.repository.campaign;

import com.pusulait.airsqreen.domain.campaign.Publisher;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by bhdr on 17.07.2017.
 */
@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    @Query("select u from Publisher u where u.id = :id")
    Optional<Plt161Publisher> findById(@Param(value = "id") Long id);

}
