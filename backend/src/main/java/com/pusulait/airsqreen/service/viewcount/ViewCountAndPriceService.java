package com.pusulait.airsqreen.service.viewcount;

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
    Double getTotalSpended(String trackToken);

    /**
     * Verilen kampanya ve bolum Id sine gore toplam harcamayi doner.
     *
     * @param compaignId
     * @param sectionId
     * @return
     */
    Double getTotalSpended(String compaignId, String sectionId);

    /**
     * Verilen takip anahtari ve tarih ararligi ile toplam harcama miktarini doner.
     *
     * @param trackToken
     * @param start
     * @param end
     * @return
     */
    Double getTotalSpendedWithDateRange(String trackToken, Date start, Date end);

    /**
     * Verilen kampanya ve bolum Id sine ve tarih araligina gore toplam harcamayi doner.
     *
     * @param compaignId
     * @param sectionId
     * @param start
     * @param end
     * @return
     */
    Double getTotalSpended(String compaignId, String sectionId, Date start, Date end);
}
