package com.pusulait.airsqreen.service;

import com.pusulait.airsqreen.domain.SystemError;
import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.dto.SystemErrorDTO;
import com.pusulait.airsqreen.domain.dto.campaign.CampaignDTO;
import com.pusulait.airsqreen.repository.CampaignRepository;
import com.pusulait.airsqreen.repository.error.SystemErrorRepository;
import com.pusulait.airsqreen.service.paltform161.Platform161Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by benan on 7/13/2017.
 */
@Slf4j
@Service
@Transactional
public class CampaignService {


    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private Platform161Service platform161Service;

    @Transactional
    public void save(CampaignDTO campaignDTO) {

        Campaign campaign = CampaignDTO.toEntity(campaignDTO);
        campaignRepository.save(campaign);
    }

    @Transactional
    public void save() {

       List<Campaign> campaignList = platform161Service.getAllCampaigns().stream().map(CampaignDTO::toEntity).collect(Collectors.toList());
        for(Campaign campaign : campaignList)
            campaignRepository.save(campaign);
    }

}