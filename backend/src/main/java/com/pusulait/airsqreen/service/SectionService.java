package com.pusulait.airsqreen.service;

import com.pusulait.airsqreen.domain.base.DataStatus;
import com.pusulait.airsqreen.domain.campaign.Section;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Section;
import com.pusulait.airsqreen.domain.dto.campaign.SectionDTO;
import com.pusulait.airsqreen.domain.dto.campaign.platform161.Plt161SectionDTO;
import com.pusulait.airsqreen.domain.dto.device.DeviceDTO;
import com.pusulait.airsqreen.domain.dto.platform.PlatformUserDTO;
import com.pusulait.airsqreen.repository.campaign.SectionRepository;
import com.pusulait.airsqreen.service.paltform161.Platform161Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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



    /**
     * get all the section  for search
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SectionDTO> search(String name, DataStatus dataStatus, Pageable pageable) throws  Exception {
        log.debug("Request to get all findByName");
        log.debug("Request to get all findByNameAndStatus");
        if(name!=null && dataStatus !=null)
            return sectionRepository.findByNameContainingIgnoreCaseAndDataStatus(name, dataStatus, pageable).map(SectionDTO::toDTO);
        else if( name!=null)
            return sectionRepository.findByNameContainingIgnoreCase(name, pageable).map(SectionDTO::toDTO);
        return sectionRepository.findByDataStatus(dataStatus, pageable).map(SectionDTO::toDTO);
    }


  /*  @Transactional
    public void saveSections() {

        List<Section> sectionList = platform161Service.getAllSections().stream().map(Plt161SectionDTO::toEntity).collect(Collectors.toList());
        for (Section section : sectionList) {
            sectionRepository.save(section);
        }
    }*/


    @Transactional
    public Section save(Plt161SectionDTO plt161SectionDTO) {
            return sectionRepository.save(Plt161SectionDTO.toEntity(plt161SectionDTO));
    }


    @Transactional
    public void updateSection(Plt161SectionDTO plt161SectionDTO) {

        Optional<Section> sectionOptional = sectionRepository.findByExternalId(plt161SectionDTO.getId());

        if (sectionOptional.isPresent()) {

            Section section = sectionOptional.get();

            if (section instanceof Plt161Section)
                section = Plt161SectionDTO.update(plt161SectionDTO, (Plt161Section) section);
            // else if (publisher instanceof XXXPublisher)
            // publisher = PublisherDTO.update(publisherDTO, (XXXPublisher) publisher);

            sectionRepository.save(section);

        }
    }



}
