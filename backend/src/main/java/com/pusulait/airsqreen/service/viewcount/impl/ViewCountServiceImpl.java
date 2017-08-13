package com.pusulait.airsqreen.service.viewcount.impl;

import com.pusulait.airsqreen.domain.dto.viewcount.ViewCountDTO;
import com.pusulait.airsqreen.domain.viewcount.ViewCount;
import com.pusulait.airsqreen.domain.viewcount.ViewCountLog;
import com.pusulait.airsqreen.repository.viewcount.ViewCountLogRepository;
import com.pusulait.airsqreen.repository.viewcount.ViewCountRepository;
import com.pusulait.airsqreen.service.viewcount.ViewCountService;
import com.pusulait.airsqreen.service.viewcount.ViewCountSpendRequirement;
import com.pusulait.airsqreen.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.pusulait.airsqreen.util.ViewCountUtil.checkParams;
import static com.pusulait.airsqreen.util.ViewCountUtil.getToken;

/**
 * Created by yildizib on 03/08/2017.
 */
@Slf4j
@Service("viewCountService")
public class ViewCountServiceImpl implements ViewCountService {

    @Autowired
    protected ViewCountRepository viewCountRepository;
    @Autowired
    protected ViewCountLogRepository viewCountLogRespository;
    @Autowired
    protected ViewCountSpendRequirement viewCountSpendRequirement;

    /**
     * Buranin apiden alinirken kayit edilmesi gerekir. O sirada token da uretilir.
     *
     * @param campaignId
     * @param sectionId
     * @return
     * @throws NullPointerException
     */
    @Transactional
    @Override
    public String save(String campaignId, String sectionId) throws NullPointerException {
        ViewCountDTO dto = null;
        if (checkParams(campaignId, sectionId)) {
            throw new NullPointerException("campaignId, sectionId   NULL veya bos olamaz!");
        }
        /* Burada disardaki sectiondan biglileri iceri alir. */
        dto = viewCountSpendRequirement.getDetail(campaignId, sectionId);
        return save(campaignId, dto.getDeviceId(), dto.getActionId(), sectionId, null);
    }

    /**
     * Buranin apiden alinirken kayit edilmesi gerekir. O sirada token da uretilir.
     *
     * @param campaignId
     * @param deviceId
     * @param actionId
     * @param sectionId
     * @param backendTrackUrl
     * @return
     * @throws NullPointerException
     */
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
        viewCount.setTotalCount(0L);
        viewCountRepository.save(viewCount);
        //
        //ViewCountLog viewCountLog = new ViewCountLog(viewCount.getId(), null);
        //viewCountLogRespository.save(viewCountLog);

        return result;
    }

    @Override
    public String getTrackToken(String campaignId, String sectionId) throws NullPointerException {
        if (checkParams(campaignId, sectionId)) {
            throw new NullPointerException("campaignId, sectionId   NULL veya bos olamaz!");
        }
        ViewCount viewCount = viewCountRepository.findByCampaignIdAndSectionId(campaignId, sectionId);
        return viewCount == null ? null : viewCount.getTrackingToken();
    }

    /**
     * end poind te inserler burada olacak. Burasi performans icin optimize
     * edilebilir istenirse. Mesela queue mantigi buraya eklenebilir.
     *
     * @param trackToken
     * @throws NullPointerException
     */
    @Transactional
    @Override
    public synchronized void incrementViewCount(String trackToken) throws NullPointerException,InterruptedException {
        if (checkParams(trackToken)) {
            throw new NullPointerException("token   NULL veya bos olamaz!");
        }
        Thread.sleep(RandomUtil.generateRandomNumber(5000));

        ViewCount viewCount = viewCountRepository.findByTrackingToken(trackToken);
        viewCount.setTotalCount(viewCount.getTotalCount() + 1);
        viewCountRepository.save(viewCount);
        //
        ViewCountLog viewCountLog = new ViewCountLog(viewCount.getId(), null);
        viewCountLogRespository.save(viewCountLog);
    }

    @Override
    public ViewCountDTO getTotalCount(String token) throws NullPointerException {
        ViewCountDTO result = null;
        if (checkParams(token)) {
            throw new NullPointerException("token, start, end  NULL veya bos olamaz!");
        }
        ViewCount viewCount = viewCountRepository.findByTrackingToken(token);
        result = new ViewCountDTO(viewCount.getCampaignId(), viewCount.getSectionId(), viewCount.getDeviceId(), viewCount.getActionId(), viewCount.getTotalCount());
        return result;
    }

    @Override
    public ViewCountDTO getTotalCount(String campaignId, String sectionId) throws NullPointerException {
        ViewCountDTO result = null;
        if (checkParams(campaignId, sectionId)) {
            throw new NullPointerException("campaignId, sectionId   NULL veya bos olamaz!");
        }
        ViewCount viewCount = viewCountRepository.findByCampaignIdAndSectionId(campaignId, sectionId);
        result = new ViewCountDTO(viewCount.getCampaignId(), viewCount.getSectionId(), viewCount.getDeviceId(), viewCount.getActionId(), viewCount.getTotalCount());
        return result;
    }

    @Override
    public ViewCountDTO getTotalCountWithDateRange(String token, Date start, Date end) throws NullPointerException {
        ViewCountDTO result = null;
        if (checkParams(token, start, end)) {
            throw new NullPointerException("token, start, end   NULL veya bos olamaz!");
        }
        ViewCount viewCount = viewCountRepository.findByTrackingToken(token);
        if (viewCount != null) {
            Long viewCountId = viewCount.getId();
            Long totalCount = viewCountLogRespository.findByTrackingTokenWithDateRange(viewCountId, start, end);
            result = new ViewCountDTO(viewCount.getCampaignId(), viewCount.getSectionId(), viewCount.getDeviceId(), viewCount.getActionId(), totalCount, start, end);

        }
        return result;
    }

    @Override
    public ViewCountDTO getTotalCountWithDateRange(String campaignId, String sectionId, Date start, Date end) throws NullPointerException {
        ViewCountDTO result = null;
        if (checkParams(campaignId, sectionId, start, end)) {
            throw new NullPointerException("campaignId, sectionId, start, end   NULL veya bos olamaz!");
        }
        ViewCount viewCount = viewCountRepository.findByCampaignIdAndSectionId(campaignId, sectionId);
        return getTotalCountWithDateRange(viewCount.getTrackingToken(), start, end);
    }

    @Override
    public List<ViewCountDTO> getTotalCountWithCampaignId(String campaignId) {
        List<ViewCountDTO> result = null;
        if (checkParams(campaignId)) {
            throw new NullPointerException("campaignId NULL veya bos olamaz!");
        }
        List<ViewCount> viewCounts = viewCountRepository.findByCampaignId(campaignId);
        if (viewCounts != null) {
            result = new LinkedList<>();
            for (ViewCount viewCount : viewCounts) {
                ViewCountDTO dto = new ViewCountDTO(viewCount.getCampaignId(), viewCount.getSectionId(), viewCount.getDeviceId(), viewCount.getActionId(), viewCount.getTotalCount());
                result.add(dto);
            }
        }
        return result;
    }

    @Override
    public List<ViewCountDTO> getTotalCountWithCampaignId(String campaignId, Date start, Date end) {
        List<ViewCountDTO> result = null;
        if (checkParams(campaignId)) {
            throw new NullPointerException("campaignId NULL veya bos olamaz!");
        }
        List<ViewCount> viewCounts = viewCountRepository.findByCampaignId(campaignId);
        if (viewCounts != null) {
            result = new LinkedList<>();
            for (ViewCount viewCount : viewCounts) {
                ViewCountDTO dto = getTotalCountWithDateRange(viewCount.getTrackingToken(), start, end);
                result.add(dto);
            }
        }
        return result;
    }

}
