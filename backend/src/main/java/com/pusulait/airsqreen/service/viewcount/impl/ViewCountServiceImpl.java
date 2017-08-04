package com.pusulait.airsqreen.service.viewcount.impl;

import com.pusulait.airsqreen.domain.dto.viewcount.ViewCountDTO;
import com.pusulait.airsqreen.domain.viewcount.ViewCount;
import com.pusulait.airsqreen.domain.viewcount.ViewCountLog;
import com.pusulait.airsqreen.repository.viewcount.ViewCountLogRespository;
import com.pusulait.airsqreen.repository.viewcount.ViewCountRepository;
import com.pusulait.airsqreen.service.viewcount.ViewCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.pusulait.airsqreen.util.ViewCountUtil.*;

/**
 * Created by yildizib on 03/08/2017.
 */
@Slf4j
@Service("viewCountService")
public class ViewCountServiceImpl implements ViewCountService {

    @Autowired
    private ViewCountRepository viewCountRepository;
    @Autowired
    private ViewCountLogRespository viewCountLogRespository;

    @Transactional
    @Override
    public String save(String campaignId, String sectionId) throws NullPointerException {
        ViewCountDTO dto = null;
        if (checkParams(campaignId, sectionId)) {
            throw new NullPointerException("campaignId, sectionId   NULL veya bos olamaz!");
        }
        /* Burada disardaki sectiondan biglileri iceri alir. */
        dto = getDetail(campaignId, sectionId);
        return save(campaignId, dto.getDeviceId(), dto.getActionId(), sectionId, null);
    }

    @Transactional
    @Override
    public String save(String campaignId, String deviceId, String actionId, String sectionId, String backendTrackUrl) throws NullPointerException {
        String result = null;
        if (checkParams(campaignId, deviceId, actionId, sectionId)) {
            throw new NullPointerException("campaignId, deviceId, actionId, sectionId    NULL veya bos olamaz!");
        }
        //
        result = getToken(campaignId, deviceId, actionId, sectionId);
        //
        ViewCount viewCount = new ViewCount(result, campaignId, deviceId, actionId, sectionId, null);
        viewCountRepository.save(viewCount);
        //
        ViewCountLog viewCountLog = new ViewCountLog(viewCount.getId(), null);
        viewCountLogRespository.save(viewCountLog);

        return result;
    }

    @Override
    public String getTrackToken(String campaignId, String sectionId) throws NullPointerException {
        String result = null;
        if (checkParams(campaignId, sectionId)) {
            throw new NullPointerException("campaignId, sectionId   NULL veya bos olamaz!");
        }
        return result;
    }

    @Transactional
    @Override
    public void incrementViewCount(String trackToken) throws NullPointerException {
        if (checkParams(trackToken)) {
            throw new NullPointerException("token   NULL veya bos olamaz!");
        }
    }

    @Override
    public List<ViewCountDTO> getTotalCount(String token) throws NullPointerException {
        List<ViewCountDTO> result = null;
        if (checkParams(token)) {
            throw new NullPointerException("token, start, end  NULL veya bos olamaz!");
        }
        return result;
    }

    @Override
    public List<ViewCountDTO> getTotalCount(String campaignId, String sectionId) throws NullPointerException {
        List<ViewCountDTO> result = null;
        if (checkParams(campaignId, sectionId)) {
            throw new NullPointerException("campaignId, sectionId   NULL veya bos olamaz!");
        }
        return result;
    }

    @Override
    public List<ViewCountDTO> getTotalCountWithDateRange(String token, Date start, Date end) throws NullPointerException {
        List<ViewCountDTO> result = null;
        if (checkParams(token, start, end)) {
            throw new NullPointerException("token, start, end   NULL veya bos olamaz!");
        }
        return result;
    }

    @Override
    public List<ViewCountDTO> getTotalCountWithDateRange(String campaignId, String sectionId, Date start, Date end) throws NullPointerException {
        List<ViewCountDTO> result = null;
        if (checkParams(campaignId, sectionId, start, end)) {
            throw new NullPointerException("campaignId, sectionId, start, end   NULL veya bos olamaz!");
        }
        return result;
    }

}
