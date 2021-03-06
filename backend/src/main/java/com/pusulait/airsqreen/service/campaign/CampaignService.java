package com.pusulait.airsqreen.service.campaign;

import com.pusulait.airsqreen.domain.base.DataStatus;
import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.CampaignConstraint;
import com.pusulait.airsqreen.domain.campaign.CampaignSection;
import com.pusulait.airsqreen.domain.campaign.Section;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;
import com.pusulait.airsqreen.domain.dto.campaign.CampaignConstraintDTO;
import com.pusulait.airsqreen.domain.dto.campaign.CampaignDTO;
import com.pusulait.airsqreen.domain.dto.campaign.platform161.Plt161CampaignDTO;
import com.pusulait.airsqreen.domain.dto.campaign.enums.PricingType;
import com.pusulait.airsqreen.domain.dto.campaign.platform161.Plt161SectionDTO;
import com.pusulait.airsqreen.predicate.CampaignPredicate;
import com.pusulait.airsqreen.repository.campaign.CampaignRepository;
import com.pusulait.airsqreen.repository.campaign.CampaignSectionRepository;
import com.pusulait.airsqreen.repository.campaign.SectionRepository;
import com.pusulait.airsqreen.service.SectionService;
import com.pusulait.airsqreen.service.paltform161.Platform161Service;
import com.pusulait.airsqreen.util.EntityUtil;
import com.pusulait.airsqreen.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
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
    public CampaignDTO save(CampaignDTO campaignDTO) {

        Plt161Campaign campaign = null;
        if(campaignDTO.getId()!=null){
            campaign = campaignRepository.findById(campaignDTO.getId());
        }
        else {
            campaign = new Plt161Campaign();
        }

        return Plt161CampaignDTO.toDTO( campaignRepository.save(CampaignDTO.toEntity(campaignDTO, campaign)));

    }

    @Transactional
    public void saveAll() {

        List<Plt161Campaign> campaignList = platform161Service.getAllCampaigns().stream().
                map(Plt161CampaignDTO::toEntity).collect(Collectors.toList())
                .stream().filter(CampaignPredicate.pricingTypePredicate(PricingType.CPM))
                .collect(Collectors.toList());

        for (Plt161Campaign plt161Campaign : campaignList) {

            plt161Campaign.setDataStatus(DataStatus.PASSIVE);
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

    private void generateCampaignSection(Long campaignId, Long sectionId, CampaignSection oldCampaignSection) {

        CampaignSection campaignSection = new CampaignSection();
        campaignSection.setCampaignId(campaignId);
        campaignSection.setSectionId(sectionId);
        if (oldCampaignSection != null) {
            campaignSection.setDeviceId(oldCampaignSection.getDeviceId());
            campaignSection.setActionId(oldCampaignSection.getActionId());
            campaignSection.setKey(oldCampaignSection.getKey());
            campaignSectionRepository.delete(oldCampaignSection);
        }
        campaignSectionRepository.save(campaignSection);
    }

    @Transactional
    public void updateCampaign(Plt161CampaignDTO campaignDTO) {

        Optional<Plt161Campaign> oldPlt161CampaignOptional = campaignRepository.findByExternalId(campaignDTO.getId());

        if (oldPlt161CampaignOptional.isPresent()) {

            Plt161Campaign oldPlt161Campaign = oldPlt161CampaignOptional.get();

                if (oldPlt161Campaign.getUpdated_at().before(campaignDTO.getUpdated_at())) {

                    Plt161Campaign newCampaign = save(campaignDTO);

                    for (Long sectionId : campaignDTO.getFiltered_section_ids()) {
                        CampaignSection oldCampaignSection =  campaignSectionRepository.findByCampaignAndSectionId(oldPlt161Campaign.getExternalId(),sectionId);
                        Section section = sectionRepository.findByExternalId(sectionId).get();
                        section = getAndCreateSection(sectionId, section,newCampaign.getPlatformUserId());
                        generateCampaignSection(newCampaign.getId(), section.getId(), oldCampaignSection);
                    }
                    campaignRepository.delete(oldPlt161Campaign.getId());
                }
        } else {
            save(campaignDTO);
        }
    }

    private Section getAndCreateSection(Long sectionId, Section section,Long platformUserId) {
        if (section == null) {
            Plt161SectionDTO plt161SectionDTO = platform161Service.getSection(null, sectionId,platformUserId);
            section = sectionService.save(plt161SectionDTO);
        }
        return section;
    }

    @Transactional
    @Scheduled(cron = "0 0 1 * * ?")
    public void updateCampaigns() {

        List<Plt161CampaignDTO> campaigns = platform161Service.getAllCampaigns()
                .stream().filter(CampaignPredicate.pricingTypePredicateDTO(PricingType.CPM))
                .collect(Collectors.toList());

        campaigns.stream().forEach(campaignDTO -> updateCampaign(campaignDTO));
    }

    @Transactional(readOnly = true)
    public Page<CampaignDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Campaigns");
        return campaignRepository.findAll(pageable).map(CampaignDTO::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<CampaignDTO> search(Boolean active, String name, Pageable pageable) throws  Exception {
        log.debug("Request to get all findByNameAndStatus");
        if( active!=null)
            return campaignRepository.findByActive(active, pageable).map(CampaignDTO::toDTO);
        else if( name!=null)
            return campaignRepository.findByNameContainingIgnoreCase(name, pageable).map(CampaignDTO::toDTO);
        else
            return campaignRepository.findAll(pageable).map(CampaignDTO::toDTO);
    }

    @Transactional(readOnly = true)
    public CampaignDTO findOne(Long id) {
        log.debug("Request to get Campaign : {}", id);
        return CampaignDTO.toDTO(campaignRepository.findOne(id));
    }



}