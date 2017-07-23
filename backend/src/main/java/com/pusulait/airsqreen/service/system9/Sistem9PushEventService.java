package com.pusulait.airsqreen.service.system9;

import com.pusulait.airsqreen.domain.dto.event.Sistem9PushEventDTO;
import com.pusulait.airsqreen.domain.event.Sistem9PushEvent;
import com.pusulait.airsqreen.repository.event.Sistem9PushEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by benan on 7/16/2017.
 */
@Service
public class Sistem9PushEventService {

    @Autowired
    private Sistem9PushEventRepository sistem9PushEventRepository;


    @Transactional
    public void save(Sistem9PushEventDTO sistem9PushEventDTO) {

        Sistem9PushEvent sistem9PushEvent = Sistem9PushEventDTO.toEntity(sistem9PushEventDTO);
        sistem9PushEventRepository.save(sistem9PushEvent);
    }


}
