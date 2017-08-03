package com.pusulait.airsqreen.service;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.CampaignSection;
import com.pusulait.airsqreen.domain.campaign.Section;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;
import com.pusulait.airsqreen.domain.dto.campaign.Plt161CampaignDTO;
import com.pusulait.airsqreen.domain.dto.section.SectionDTO;
import com.pusulait.airsqreen.repository.campaign.CampaignRepository;
import com.pusulait.airsqreen.repository.campaign.CampaignSectionRepository;
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
 * Created by benan on 7/13/2017.
 */
@Slf4j
@Service
@Transactional
public class CampaignService {

    @Autowired
    private SectionService sectionService;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CampaignSectionRepository campaignSectionRepository;

    @Autowired
    private Platform161Service platform161Service;

    @Transactional
    public void save(Plt161CampaignDTO campaignDTO) {

        Campaign campaign = Plt161CampaignDTO.toEntity(campaignDTO);
        campaignRepository.save(campaign);
    }

    @Transactional
    public void save() {

        List<Campaign> campaignList = platform161Service.getAllCampaigns().stream().map(Plt161CampaignDTO::toEntity).collect(Collectors.toList());
        for (Campaign campaign : campaignList)
            campaignRepository.save(campaign);
    }

    @Transactional
    public void savePlt161(Integer recordCount) {

        List<Plt161Campaign> campaignList = platform161Service.getAllCampaigns().stream().map(Plt161CampaignDTO::toEntity).collect(Collectors.toList());

        if (recordCount != null) {
            campaignList = campaignList.subList(0, recordCount);
        }

        for (Plt161Campaign plt161Campaign : campaignList){
            campaignRepository.save(plt161Campaign);
        }
    }

    @Transactional
    public void savePlt161() {
        // save everything
        savePlt161(null);
    }

    @Transactional
    public void updateCampaign(Plt161CampaignDTO campaignDTO) {

        Optional<Campaign> campaignOptional = campaignRepository.findByExternalId(campaignDTO.getId());

        if (campaignOptional.isPresent()) {

            Campaign campaign = campaignOptional.get();

            if (campaign instanceof Plt161Campaign) {
                Plt161Campaign plt161Campaign = (Plt161Campaign) campaign;
                campaign = Plt161CampaignDTO.update(campaignDTO, (Plt161Campaign) campaign);
                if(plt161Campaign.getUpdated_at().before(campaignDTO.getUpdated_at())){


                    for(Long sectionId : campaignDTO.getFiltered_section_ids()){

                        Section section = sectionRepository.findOne(sectionId);

                        if(section == null){

                            SectionDTO sectionDTO = platform161Service.getSection(null,sectionId);
                            section = sectionService.save(sectionDTO);
                            CampaignSection campaignSection = new CampaignSection();
                            campaignSection.setCampaign(campaign);
                            campaignSection.setSection(section);
                        }

                        CampaignSection campaignSection = new CampaignSection();
                        campaignSection.setCampaign(campaign);
                        campaignSection.setSection(section);
                        campaignSectionRepository.save(campaignSection);
                    }

                    campaignRepository.delete(campaign.getId());
                    save(campaignDTO);
                }
            }
            campaignRepository.save(campaign);
        }
    }

    @Transactional
    public void updateCampaigns() {

        platform161Service.getAllCampaigns().forEach(campaignDTO -> updateCampaign(campaignDTO));

    }


}