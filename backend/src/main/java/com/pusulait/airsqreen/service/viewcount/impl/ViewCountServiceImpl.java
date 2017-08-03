package com.pusulait.airsqreen.service.viewcount.impl;

import com.pusulait.airsqreen.domain.dto.viewcount.ViewCountDTO;
import com.pusulait.airsqreen.service.viewcount.ViewCountService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by yildizib on 03/08/2017.
 */
@Service("viewCountService")
public class ViewCountServiceImpl implements ViewCountService {
    @Override
    public String save(String compaignId, String sectionId) {
        return null;
    }

    @Override
    public String save(String compaignId, String deviceId, String actionId, String sectionId, String backendTrackUrl) {
        return null;
    }

    @Override
    public String getTrackToken(String compaignId, String sectionId) {
        return null;
    }

    @Override
    public void incrementViewCount(String trackToken) {

    }

    @Override
    public List<ViewCountDTO> getTotalCount(String token) {
        return null;
    }

    @Override
    public List<ViewCountDTO> getTotalCount(String compaignId, String sectionId) {
        return null;
    }

    @Override
    public List<ViewCountDTO> getTotalCountWithDateRange(String token, Date start, Date end) {
        return null;
    }

    @Override
    public List<ViewCountDTO> getTotalCountWithDateRange(String compaignId, String sectionId, Date start, Date end) {
        return null;
    }
}
