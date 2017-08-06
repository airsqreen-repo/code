package com.pusulait.airsqreen.service.viewcount.impl;

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
        Double unitPrice = viewCountSpendRequirement.getUnitPrice(null, null);
        if (checkParams(trackToken)) {
            throw new NullPointerException("trackToken NULL veya bos olamaz!");
        }
        return result;
    }

    @Override
    public Double getTotalSpended(String campaignId, String sectionId) throws NullPointerException {
        Double result = null;
        Double unitPrice = viewCountSpendRequirement.getUnitPrice(campaignId, sectionId);
        if (checkParams(campaignId, sectionId)) {
            throw new NullPointerException("campaignId, sectionId  NULL veya bos olamaz!");
        }
        return result;
    }

    @Override
    public Double getTotalSpendedWithDateRange(String trackToken, Date start, Date end) throws NullPointerException {
        Double result = null;
        Double unitPrice = viewCountSpendRequirement.getUnitPrice(null, null);
        if (checkParams(trackToken, start, end)) {
            throw new NullPointerException("trackToken, start, end   NULL veya bos olamaz!");
        }
        return result;
    }

    @Override
    public Double getTotalSpended(String campaignId, String sectionId, Date start, Date end) throws NullPointerException {
        Double result = null;
        Double unitPrice = viewCountSpendRequirement.getUnitPrice(campaignId, sectionId);
        if (checkParams(campaignId, sectionId, start, end)) {
            throw new NullPointerException("campaignId, sectionId, start, end   NULL veya bos olamaz!");
        }
        return result;
    }
}
