package com.pusulait.airsqreen.service.viewcount;

import java.util.Date;

/**
 * Created by yildizib on 03/08/2017.
 */
public interface ViewCountAndPriceService extends ViewCountService {
    /**
     * Verilen takip anahtari ile toplam harcama miktarini doner.
     *
     * @param trackToken
     * @return
     */
    Double getTotalSpent(String trackToken);

    /**
     * Verilen kampanya ve bolum Id sine gore toplam harcamayi doner.
     *
     * @param campaignId
     * @param sectionId
     * @return
     */
    Double getTotalSpent(String campaignId, String sectionId);

    /**
     * Verilen takip anahtari ve tarih ararligi ile toplam harcama miktarini doner.
     *
     * @param trackToken
     * @param start
     * @param end
     * @return
     */
    Double getTotalSpentWithDateRange(String trackToken, Date start, Date end);

    /**
     * Verilen kampanya ve bolum Id sine ve tarih araligina gore toplam harcamayi doner.
     *
     * @param campaignId
     * @param sectionId
     * @param start
     * @param end
     * @return
     */
    Double getTotalSpent(String campaignId, String sectionId, Date start, Date end);
}
