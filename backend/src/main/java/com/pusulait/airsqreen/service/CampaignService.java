package com.pusulait.airsqreen.service;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.CampaignSection;
import com.pusulait.airsqreen.domain.campaign.Section;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;
import com.pusulait.airsqreen.domain.dto.campaign.CampaignDTO;
import com.pusulait.airsqreen.domain.dto.campaign.Plt161CampaignDTO;
import com.pusulait.airsqreen.domain.dto.campaign.enums.PricingType;
import com.pusulait.airsqreen.domain.dto.section.SectionDTO;
import com.pusulait.airsqreen.domain.viewcount.ViewCount;
import com.pusulait.airsqreen.predicate.CampaignPredicate;
import com.pusulait.airsqreen.repository.campaign.CampaignRepository;
import com.pusulait.airsqreen.repository.campaign.CampaignSectionRepository;
import com.pusulait.airsqreen.repository.campaign.SectionRepository;
import com.pusulait.airsqreen.repository.viewcount.ViewCountRepository;
import com.pusulait.airsqreen.service.paltform161.Platform161Service;
import com.pusulait.airsqreen.service.viewcount.ViewCountAndPriceService;
import com.pusulait.airsqreen.util.EntityUtil;
import com.pusulait.airsqreen.util.StringUtils;
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


    /*@Autowired
    private ViewCountRepository viewCountRepository;
*/
    @Transactional
    public Plt161Campaign save(Plt161CampaignDTO campaignDTO) {

        Plt161Campaign campaign = Plt161CampaignDTO.toEntity(campaignDTO);
        return campaignRepository.save(campaign);
    }

    @Transactional
    public void save() {

        List<Plt161Campaign> campaignList = platform161Service.getAllCampaigns().stream().
                map(Plt161CampaignDTO::toEntity).collect(Collectors.toList())
                .stream().filter(CampaignPredicate.pricingTypePredicate(PricingType.CPM))
                .collect(Collectors.toList());

        for (Plt161Campaign plt161Campaign : campaignList) {

            campaignRepository.save(plt161Campaign);
            Section section = null;
            for (Long sectionId : EntityUtil.buildLongArray(plt161Campaign.getFiltered_section_ids())) {

                if (!StringUtils.isEmpty(sectionId)) {
                    section = sectionRepository.findOne(sectionId);
                    section = getAndCreateSection(sectionId, section,plt161Campaign.getPlatformUserId());
                    generateCampaignSection(plt161Campaign.getId(), section.getId(), null);
                }
            }
        }
    }

    private void generateCampaignSection(Long id, Long id2, CampaignSection oldCampaignSection) {

        CampaignSection campaignSection = new CampaignSection();
        campaignSection.setCampaignId(id);
        campaignSection.setSectionId(id2);
        if (oldCampaignSection != null) {
            campaignSection.setDeviceId(campaignSection.getDeviceId());
            campaignSection.setActionId(campaignSection.getActionId());
            campaignSection.setKey(campaignSection.getKey());
        }
        campaignSectionRepository.save(campaignSection);
    }


    @Transactional
    public void updateCampaign(Plt161CampaignDTO campaignDTO) {

        Optional<Campaign> campaignOptional = campaignRepository.findByExternalId(campaignDTO.getId());

        if (campaignOptional.isPresent()) {

            Campaign oldCampaign = campaignOptional.get();

            if (oldCampaign instanceof Plt161Campaign) {
                Plt161Campaign oldPlt161Campaign = (Plt161Campaign) oldCampaign;
                CampaignSection oldCampaignSection = oldPlt161Campaign.getCampaignSections().get(0);
                if (oldPlt161Campaign.getUpdated_at().before(campaignDTO.getUpdated_at())) {

                    Plt161Campaign newCampaign = save(campaignDTO);

                    for (Long sectionId : campaignDTO.getFiltered_section_ids()) {

                        Section section = sectionRepository.findOne(sectionId);
                        section = getAndCreateSection(sectionId, section,newCampaign.getPlatformUserId());
                        generateCampaignSection(newCampaign.getId(), section.getId(), oldCampaignSection);
                    }
                    campaignRepository.delete(oldCampaign.getId());
                }
            }
        } else {
            save(campaignDTO);
        }
    }

    private Section getAndCreateSection(Long sectionId, Section section,Long platformUserId) {
        if (section == null) {
            SectionDTO sectionDTO = platform161Service.getSection(null, sectionId,platformUserId);
            section = sectionService.save(sectionDTO);
        }
        return section;
    }

    @Transactional
    public void updateCampaigns() {

        List<Plt161CampaignDTO> campaigns = platform161Service.getAllCampaigns()
                .stream().filter(CampaignPredicate.pricingTypePredicateDTO(PricingType.CPM))
                .collect(Collectors.toList());

        campaigns.stream().forEach(campaignDTO -> updateCampaign(campaignDTO));
    }


}