package com.pusulait.airsqreen.service.viewcount.impl;

import com.pusulait.airsqreen.domain.dto.viewcount.ViewCountDTO;
import com.pusulait.airsqreen.domain.viewcount.ViewCount;
import com.pusulait.airsqreen.service.viewcount.ViewCountAndPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.pusulait.airsqreen.util.ViewCountUtil.checkParams;


/**
 * Created by yildizib on 03/08/2017.
 */
@Slf4j
@Service("viewCountAndPriceService")
public class ViewCountAndPriceServiceImpl extends ViewCountServiceImpl implements ViewCountAndPriceService {
    @Override
    public Double getTotalSpended(String trackToken) throws NullPointerException {
        Double result = null;
        if (checkParams(trackToken)) {
            throw new NullPointerException("trackToken NULL veya bos olamaz!");
        }
        ViewCount viewCount = viewCountRepository.findByTrackingToken(trackToken);
        if (viewCount != null) {
            result = getTotalSpended(viewCount.getCampaignId(), viewCount.getCampaignSectionId());
        }
        return result;
    }

    @Override
    public Double getTotalSpended(String campaignId, String sectionId) throws NullPointerException {
        Double result = null;
        if (checkParams(campaignId, sectionId)) {
            throw new NullPointerException("campaignId, sectionId  NULL veya bos olamaz!");
        }
        ViewCountDTO countDTO = getTotalCount(campaignId, sectionId);
        Double unitPrice = viewCountSpendRequirement.getUnitPrice(campaignId, sectionId);
        if (countDTO != null && unitPrice != null) {
            Long totalCount = countDTO.getTotalCount();
            if (totalCount != null) {
                result = totalCount * unitPrice;
            }
        }
        return result;
    }

    @Override
    public Double getTotalSpendedWithDateRange(String trackToken, Date start, Date end) throws NullPointerException {
        Double result = null;
        Double unitPrice = null;
        if (checkParams(trackToken, start, end)) {
            throw new NullPointerException("trackToken, start, end   NULL veya bos olamaz!");
        }
        ViewCountDTO countDTO = getTotalCountWithDateRange(trackToken, start, end);
        if (countDTO != null) {
            unitPrice = viewCountSpendRequirement.getUnitPrice(countDTO.getCampaignId(), countDTO.getCampaignSectionId());
            result = countDTO.getTotalCount() * unitPrice;
        }

        return result;
    }

    @Override
    public Double getTotalSpended(String campaignId, String sectionId, Date start, Date end) throws NullPointerException {
        Double result = null;
        if (checkParams(campaignId, sectionId, start, end)) {
            throw new NullPointerException("campaignId, sectionId, start, end   NULL veya bos olamaz!");
        }
        ViewCount viewCount = viewCountRepository.findByCampaignIdAndCampaignSectionId(campaignId, sectionId);
        if (viewCount != null) {
            result = getTotalSpendedWithDateRange(viewCount.getTrackingToken(), start, end);
        }
        return result;
    }
}
