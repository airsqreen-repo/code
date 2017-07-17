package com.pusulait.airsqreen.service;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.Publisher;
import com.pusulait.airsqreen.domain.dto.campaign.CampaignDTO;
import com.pusulait.airsqreen.domain.dto.publisher.PublisherDTO;
import com.pusulait.airsqreen.repository.campaign.CampaignRepository;
import com.pusulait.airsqreen.repository.campaign.PublisherRepository;
import com.pusulait.airsqreen.service.paltform161.Platform161Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by bhdr on 17.07.2017.
 */
@Slf4j
@Service
@Transactional
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private Platform161Service platform161Service;

    @Transactional
    public void savePublishers() {

        List<Publisher> publisherList = platform161Service.getAllPublishers().stream().map(PublisherDTO::toEntity).collect(Collectors.toList());
        for(Publisher publisher : publisherList)
            publisherRepository.save(publisher);
    }

    @Transactional
    public void updatePublisher(PublisherDTO publisherDTO) {

        Optional<Publisher> publisherOptional  = publisherRepository.findById(publisherDTO.getId());

        if(publisherOptional.isPresent()){

            Publisher publisher = publisherOptional.get();
            publisher = PublisherDTO.toEntity(publisherDTO,publisher);
            publisherRepository.save(publisher);

        }
    }

    @Transactional
    public void updatePublishers() {

        List<Publisher> publisherList = platform161Service.getAllPublishers().stream().map(PublisherDTO::toEntity).collect(Collectors.toList());

        platform161Service.getAllPublishers().forEach(publisherDTO -> updatePublisher(publisherDTO));


    }


}
