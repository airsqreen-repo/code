package com.pusulait.airsqreen.service;

import com.pusulait.airsqreen.domain.campaign.Section;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Section;
import com.pusulait.airsqreen.domain.dto.section.SectionDTO;
import com.pusulait.airsqreen.repository.campaign.SectionRepository;
import com.pusulait.airsqreen.service.paltform161.Platform161Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by bhdr on 16.07.2017.
 */
@Slf4j
@Service
@Transactional
public class SectionService {

    @Autowired
    private Platform161Service platform161Service;

    @Autowired
    private SectionRepository sectionRepository;

  /*  @Transactional
    public void saveSections() {

        List<Section> sectionList = platform161Service.getAllSections().stream().map(SectionDTO::toEntity).collect(Collectors.toList());
        for (Section section : sectionList) {
            sectionRepository.save(section);
        }
    }*/


    @Transactional
    public Section save(SectionDTO sectionDTO) {
            return sectionRepository.save(SectionDTO.toEntity(sectionDTO));
    }


    @Transactional
    public void updateSection(SectionDTO sectionDTO) {

        Optional<Section> sectionOptional = sectionRepository.findByExternalId(sectionDTO.getId());

        if (sectionOptional.isPresent()) {

            Section section = sectionOptional.get();

            if (section instanceof Plt161Section)
                section = SectionDTO.update(sectionDTO, (Plt161Section) section);
            // else if (publisher instanceof XXXPublisher)
            // publisher = PublisherDTO.update(publisherDTO, (XXXPublisher) publisher);

            sectionRepository.save(section);

        }
    }



}
