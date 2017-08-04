package com.pusulait.airsqreen.service.viewcount.impl;

import com.pusulait.airsqreen.domain.dto.viewcount.ViewCountDTO;
import com.pusulait.airsqreen.service.viewcount.ViewCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.pusulait.airsqreen.util.ViewCountUtil.checkParams;

/**
 * Created by yildizib on 03/08/2017.
 */
@Slf4j
@Service("viewCountService")
public class ViewCountServiceImpl implements ViewCountService {

    @Transactional
    @Override
    public String save(String campaignId, String sectionId) {
        String result = null;
        if (checkParams(campaignId, sectionId)) {
            throw new NullPointerException("campaignId, sectionId   NULL veya bos olamaz!");
        }
        return result;
    }

    @Transactional
    @Override
    public String save(String campaignId, String deviceId, String actionId, String sectionId, String backendTrackUrl) {
        String result = null;
        if (checkParams(campaignId, deviceId, actionId, sectionId)) {
            throw new NullPointerException("campaignId, deviceId, actionId, sectionId    NULL veya bos olamaz!");
        }
        return result;
    }

    @Override
    public String getTrackToken(String campaignId, String sectionId) {
        String result = null;
        if (checkParams(campaignId, sectionId)) {
            throw new NullPointerException("campaignId, sectionId   NULL veya bos olamaz!");
        }
        return result;
    }

    @Transactional
    @Override
    public void incrementViewCount(String trackToken) {
        if (checkParams(trackToken)) {
            throw new NullPointerException("token   NULL veya bos olamaz!");
        }
    }

    @Override
    public List<ViewCountDTO> getTotalCount(String token) {
        List<ViewCountDTO> result = null;
        if (checkParams(token)) {
            throw new NullPointerException("token, start, end  NULL veya bos olamaz!");
        }
        return result;
    }

    @Override
    public List<ViewCountDTO> getTotalCount(String campaignId, String sectionId) {
        List<ViewCountDTO> result = null;
        if (checkParams(campaignId, sectionId)) {
            throw new NullPointerException("campaignId, sectionId   NULL veya bos olamaz!");
        }
        return result;
    }

    @Override
    public List<ViewCountDTO> getTotalCountWithDateRange(String token, Date start, Date end) {
        List<ViewCountDTO> result = null;
        if (checkParams(token, start, end)) {
            throw new NullPointerException("token, start, end   NULL veya bos olamaz!");
        }
        return result;
    }

    @Override
    public List<ViewCountDTO> getTotalCountWithDateRange(String campaignId, String sectionId, Date start, Date end) {
        List<ViewCountDTO> result = null;
        if (checkParams(campaignId, sectionId, start, end)) {
            throw new NullPointerException("campaignId, sectionId, start, end   NULL veya bos olamaz!");
        }
        return result;
    }

}
