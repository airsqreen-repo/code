package com.pusulait.airsqreen.service.viewcount.impl;

import com.pusulait.airsqreen.service.viewcount.ViewCountAndPriceService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by yildizib on 03/08/2017.
 */
@Service("viewCountAndPriceService")
public class ViewCountAndPriceServiceImpl extends ViewCountServiceImpl implements ViewCountAndPriceService {
    @Override
    public Double getTotalSpended(String trackToken) {
        return null;
    }

    @Override
    public Double getTotalSpended(String compaignId, String sectionId) {
        return null;
    }

    @Override
    public Double getTotalSpendedWithDateRange(String trackToken, Date start, Date end) {
        return null;
    }

    @Override
    public Double getTotalSpended(String compaignId, String sectionId, Date start, Date end) {
        return null;
    }
}
