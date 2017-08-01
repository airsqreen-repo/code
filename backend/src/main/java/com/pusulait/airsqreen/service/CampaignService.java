package com.pusulait.airsqreen.service;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;
import com.pusulait.airsqreen.domain.dto.campaign.Plt161CampaignDTO;
import com.pusulait.airsqreen.repository.campaign.CampaignRepository;
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
    private CampaignRepository campaignRepository;

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
                campaign = Plt161CampaignDTO.update(campaignDTO, (Plt161Campaign) campaign);
                if(campaign.getLastModifiedDate().before(campaignDTO.getEndOn())){  // TODO son update tarihi nerden?
                    //todo SECTION IDLER NERDEN GELİYO. GELENLER YOK İSE 0 DAN ÇEKİLİP KAYDEDİKECEK BUNA BAĞLANACAK. YOKSA DİREK BAĞLANACAK.
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