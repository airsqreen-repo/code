package com.pusulait.airsqreen.service.campaign;

import com.pusulait.airsqreen.domain.campaign.CampaignSection;
import com.pusulait.airsqreen.domain.dto.campaign.CampaignSectionDTO;
import com.pusulait.airsqreen.repository.campaign.CampaignSectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class CampaignSectionService {

    @Autowired
    private CampaignSectionRepository campaignSectionRepository;

    @Transactional
    public CampaignSectionDTO save(CampaignSectionDTO campaignSectionDTO) {
        CampaignSection campaignSection = null;
        if(campaignSectionDTO.getId()!=null){
            campaignSection = campaignSectionRepository.findOne(campaignSectionDTO.getId());
        }
        else {
            campaignSection = new CampaignSection();
        }
        return CampaignSectionDTO.toDTO(campaignSectionRepository.save(CampaignSectionDTO.toEntity(campaignSectionDTO, campaignSection)));
    }

    @Transactional(readOnly = true)
    public Page<CampaignSectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CampaignSections");
        return campaignSectionRepository.findAll(pageable).map(CampaignSectionDTO::toDTO);
    }

    @Transactional(readOnly = true)
    public CampaignSectionDTO findOne(Long id) {
        log.debug("Request to get CampaignSection : {}", id);
        CampaignSectionDTO campaignSectionDTO = CampaignSectionDTO.toDTO(campaignSectionRepository.findOne(id));
        return campaignSectionDTO;
    }

    public void delete(Long id) {
        log.debug("Request to delete CampaignSection : {}", id);
        campaignSectionRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<CampaignSectionDTO> findAll() {
        log.debug("Request to get all CampaignSections");
        return campaignSectionRepository.findAll().stream().map(CampaignSectionDTO::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CampaignSectionDTO> findByCampaignId(Long campaingId) {
        log.debug("Request to get all findByCampaignId");
        return campaignSectionRepository.findByCampaignId(campaingId).stream().map(CampaignSectionDTO::toDTO).collect(Collectors.toList());
    }





}
