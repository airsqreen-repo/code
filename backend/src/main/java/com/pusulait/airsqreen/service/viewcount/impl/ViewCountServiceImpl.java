package com.pusulait.airsqreen.service.viewcount.impl;

import com.pusulait.airsqreen.domain.dto.viewcount.ViewCountDTO;
import com.pusulait.airsqreen.service.viewcount.ViewCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by yildizib on 03/08/2017.
 */
@Slf4j
@Service("viewCountService")
public class ViewCountServiceImpl implements ViewCountService {

    @Transactional
    @Override
    public String save(String campaignId, String sectionId) {
        return null;
    }

    @Transactional
    @Override
    public String save(String campaignId, String deviceId, String actionId, String sectionId, String backendTrackUrl) {
        return null;
    }

    @Override
    public String getTrackToken(String campaignId, String sectionId) {
        return null;
    }

    @Transactional
    @Override
    public void incrementViewCount(String trackToken) {

    }

    @Override
    public List<ViewCountDTO> getTotalCount(String token) {
        return null;
    }

    @Override
    public List<ViewCountDTO> getTotalCount(String campaignId, String sectionId) {
        return null;
    }

    @Override
    public List<ViewCountDTO> getTotalCountWithDateRange(String token, Date start, Date end) {
        return null;
    }

    @Override
    public List<ViewCountDTO> getTotalCountWithDateRange(String campaignId, String sectionId, Date start, Date end) {
        return null;
    }
}
