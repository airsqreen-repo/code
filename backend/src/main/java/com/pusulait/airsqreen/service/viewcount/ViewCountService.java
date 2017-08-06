package com.pusulait.airsqreen.service.viewcount;

import com.pusulait.airsqreen.domain.dto.viewcount.ViewCountDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by yildizib on 03/08/2017.
 */
public interface ViewCountService {

    /**
     * Takip anahtarini doner.
     * device, action id degerleri tablolardan alinir.
     *
     * @param campaignId
     * @param sectionId
     * @return
     */
    String save(String campaignId, String sectionId);

    /**
     * Takip anahtarini doner.
     * backendTrackUrl haric diger alanlar zorunludur.
     *
     * backendTrackUrl dis sistemde de sayilmasini onaylatmak icin
     * kullanilacaktir. backendTrackUrl NULL olabilir.
     *
     * @param campaignId
     * @param deviceId
     * @param actionId
     * @param sectionId
     * @param backendTrackUrl
     * @return
     */
    String save(String campaignId, String deviceId, String actionId, String sectionId, String backendTrackUrl);

    /**
     * Daha once kaydi olusan anahtari getirmek icin kullanilir.
     *
     * @param campaignId
     * @param sectionId
     * @return
     */
    String getTrackToken(String campaignId, String sectionId);

    /**
     * Gosterim adedini verilen anahtara gore arttirir.
     *
     * @param trackToken
     */
    void incrementViewCount(String trackToken);

    /**
     * Verilen takip anahtarina gore toplam gosterimi doner.
     *
     * @param token
     * @return
     */
    ViewCountDTO getTotalCount(String token);

    /**
     * Verilen kampanya ve bolum ID sine gore toplam gosterimi doner.
     *
     * @param campaignId
     * @param sectionId
     * @return
     */
    ViewCountDTO getTotalCount(String campaignId, String sectionId);

    /**
     * Verilen takip anahtarina ve tarih araligina gore toplam gosterimi doner.
     *
     * @param token
     * @param start
     * @param end
     * @return
     */
    ViewCountDTO getTotalCountWithDateRange(String token, Date start, Date end);

    /**
     * Verilen kampanya ve bolum ID sine ve tarih araligina gore toplam gosterimi doner.
     *
     * @param campaignId
     * @param sectionId
     * @param start
     * @param end
     * @return
     */
    List<ViewCountDTO> getTotalCountWithDateRange(String campaignId, String sectionId, Date start, Date end);

    /**
     * @param campaignId
     * @return
     */
    List<ViewCountDTO> getTotalCountWithCampaingId(String campaignId);


}
