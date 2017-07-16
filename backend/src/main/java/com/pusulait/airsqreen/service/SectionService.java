package com.pusulait.airsqreen.service;

import com.pusulait.airsqreen.domain.campaign.InventorySource2;
import com.pusulait.airsqreen.domain.campaign.Section;
import com.pusulait.airsqreen.domain.dto.campaign.InventorySourceDTO2;
import com.pusulait.airsqreen.domain.dto.section.SectionDTO;
import com.pusulait.airsqreen.repository.campaign.InventorySource2Repository;
import com.pusulait.airsqreen.repository.campaign.SectionRepository;
import com.pusulait.airsqreen.service.paltform161.Platform161Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Transactional
    public void saveSections() {

        List<Section> sectionList = platform161Service.getAllSections().stream().map(SectionDTO::toEntity).collect(Collectors.toList());
        for (Section section : sectionList)
            sectionRepository.save(section);
    }


}
