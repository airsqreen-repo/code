package com.pusulait.airsqreen.service.campaign;

import com.pusulait.airsqreen.domain.campaign.Campaign;
import com.pusulait.airsqreen.domain.campaign.CampaignConstraint;
import com.pusulait.airsqreen.domain.campaign.CampaignConstraint;
import com.pusulait.airsqreen.domain.campaign.CampaignSection;
import com.pusulait.airsqreen.domain.campaign.sistem9.Device;
import com.pusulait.airsqreen.domain.dto.campaign.CampaignConstraintDTO;
import com.pusulait.airsqreen.domain.dto.campaign.CampaignConstraintDTO;
import com.pusulait.airsqreen.domain.dto.weather.WeatherDTO;
import com.pusulait.airsqreen.domain.enums.CampaignConstraintFilter;
import com.pusulait.airsqreen.domain.enums.CampaignConstraintType;
import com.pusulait.airsqreen.repository.campaign.CampaignConstraintRepository;
import com.pusulait.airsqreen.service.weather.WeatherService;
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
public class CampaignConstraintService {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private CampaignConstraintRepository campaignConstraintRepository;


    @Transactional
    public CampaignConstraintDTO save(CampaignConstraintDTO campaignSectionDTO) {
        CampaignConstraint campaignConstraint = null;
        if (campaignSectionDTO.getId() != null) {
            campaignConstraint = campaignConstraintRepository.findOne(campaignSectionDTO.getId());
        } else {
            campaignConstraint = new CampaignConstraint();
        }
        ;
        return CampaignConstraintDTO.toDTO(campaignConstraintRepository.save(CampaignConstraintDTO.toEntity(campaignSectionDTO, campaignConstraint)));
    }

    @Transactional(readOnly = true)
    public Page<CampaignConstraintDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CampaignConstraints");
        return campaignConstraintRepository.findAll(pageable).map(CampaignConstraintDTO::toDTO);
    }

    @Transactional(readOnly = true)
    public CampaignConstraintDTO findOne(Long id) {
        log.debug("Request to get CampaignConstraint : {}", id);
        CampaignConstraintDTO campaignSectionDTO = CampaignConstraintDTO.toDTO(campaignConstraintRepository.findOne(id));
        return campaignSectionDTO;
    }

    public void delete(Long id) {
        log.debug("Request to delete CampaignConstraint : {}", id);
        campaignConstraintRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<CampaignConstraintDTO> findAll() {
        log.debug("Request to get all CampaignConstraints");
        return campaignConstraintRepository.findAll().stream().map(CampaignConstraintDTO::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CampaignConstraintDTO> findByCampaignId(Long campaingId) {
        log.debug("Request to get all findByCampaignId");
        return campaignConstraintRepository.findByCampaignId(campaingId).stream().map(CampaignConstraintDTO::toDTO).collect(Collectors.toList());
    }


    public Boolean campaignControlsPassed(CampaignSection campaignSection) {

        Campaign campaign = campaignSection.getCampaign();
        Device device = campaignSection.getDevice();
        Boolean result = false;

        for (CampaignConstraint campaignConstraint : campaign.getCampaignConstraints()) {

            if (campaignConstraint.getCampaignConstraintType().equals(CampaignConstraintType.TEMPERATURE)) {

                WeatherDTO weatherDTO = weatherService.getTempWithGeoCoordinates(device.getLatitude(), device.getLongitude(), true);

                result = result & checkConstraintFilter(campaignConstraint, weatherDTO, CampaignConstraintType.TEMPERATURE);

            }
            if (campaignConstraint.getCampaignConstraintType().equals(CampaignConstraintType.PRESSURE)) {

                WeatherDTO weatherDTO = weatherService.getTempWithGeoCoordinates(device.getLatitude(), device.getLongitude(), true);

                //result = result & checkConstraintFilter(campaignConstraint, weatherDTO);

            }
            if (campaignConstraint.getCampaignConstraintType().equals(CampaignConstraintType.WIND)) {

                WeatherDTO weatherDTO = weatherService.getTempWithGeoCoordinates(device.getLatitude(), device.getLongitude(), true);

                //result = result & checkConstraintFilter(campaignConstraint, weatherDTO);

            }
        }

        return true;
    }

    private Boolean checkConstraintFilter(CampaignConstraint campaignConstraint, WeatherDTO weatherDTO, CampaignConstraintType campaignConstraintType) {

        if(campaignConstraintType.equals(CampaignConstraintType.SNOW)){
            if(weatherDTO.getMain().equals("Snow")){
                return true;
            }else{
                return false;
            }
        }


        double valueInDB = Double.valueOf(campaignConstraint.getFilter_detail());
        double valueInReal = weatherDTO.getTemp().doubleValue();

        if (campaignConstraintType.equals(CampaignConstraintType.TEMPERATURE)) {
            valueInReal = weatherDTO.getTemp().doubleValue();

        } else if (campaignConstraintType.equals(CampaignConstraintType.WIND)) {
            valueInReal = weatherDTO.getWindDeg().doubleValue();

        } else if (campaignConstraintType.equals(CampaignConstraintType.PRESSURE)) {
            valueInReal = weatherDTO.getPressure().doubleValue();
        }

        if (campaignConstraint.getCampaignConstraintFilter().equals(CampaignConstraintFilter.BIGGER)) {
            if (valueInReal > valueInDB) {
                return true;
            }
        }
        if (campaignConstraint.getCampaignConstraintFilter().equals(CampaignConstraintFilter.BIGGER_EQUAL)) {
            if (valueInReal >= valueInDB) {
                return true;
            }
        }

        if (campaignConstraint.getCampaignConstraintFilter().equals(CampaignConstraintFilter.EQUAL)) {
            if (valueInReal == valueInDB) {
                return true;
            }
        }

        if (campaignConstraint.getCampaignConstraintFilter().equals(CampaignConstraintFilter.SMALLER)) {
            if (valueInReal < valueInDB) {
                return true;
            }
        }

        if (campaignConstraint.getCampaignConstraintFilter().equals(CampaignConstraintFilter.SMALLER_EQUAL)) {
            if (valueInReal <= valueInDB) {
                return true;
            }
        }

        return false;
    }


}
