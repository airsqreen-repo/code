package com.pusulait.airsqreen.service.viewcount.impl;

import com.pusulait.airsqreen.service.viewcount.ViewCountAndPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by yildizib on 03/08/2017.
 */
@Slf4j
@Service("viewCountAndPriceService")
public class ViewCountAndPriceServiceImpl extends ViewCountServiceImpl implements ViewCountAndPriceService {
    @Override
    public Double getTotalSpended(String trackToken) {
        return null;
    }

    @Override
    public Double getTotalSpended(String campaignId, String sectionId) {
        return null;
    }

    @Override
    public Double getTotalSpendedWithDateRange(String trackToken, Date start, Date end) {
        return null;
    }

    @Override
    public Double getTotalSpended(String campaignId, String sectionId, Date start, Date end) {
        return null;
    }
}
