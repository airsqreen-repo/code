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
     * @param compaignId
     * @param sectionId
     * @return
     */
    String save(String compaignId, String sectionId);

    /**
     * Takip anahtarini doner.
     * Tum alanlar zorunludur.
     *
     * @param compaignId
     * @param deviceId
     * @param actionId
     * @param sectionId
     * @return
     */
    String save(String compaignId, String deviceId, String actionId, String sectionId);

    /**
     * Daha once kaydi olusan anahtari getirmek icin kullanilir.
     *
     * @param compaignId
     * @param sectionId
     * @return
     */
    String getTrackToken(String compaignId, String sectionId);

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
    List<ViewCountDTO> getTotalCount(String token);

    /**
     * Verilen kampanya ve bolum ID sine gore toplam gosterimi doner.
     *
     * @param compaignId
     * @param sectionId
     * @return
     */
    List<ViewCountDTO> getTotalCount(String compaignId, String sectionId);

    /**
     * Verilen takip anahtarina ve tarih araligina gore toplam gosterimi doner.
     *
     * @param token
     * @param start
     * @param end
     * @return
     */
    List<ViewCountDTO> getTotalCountWithDateRange(String token, Date start, Date end);

    /**
     * Verilen kampanya ve bolum ID sine ve tarih araligina gore toplam gosterimi doner.
     *
     * @param compaignId
     * @param sectionId
     * @param start
     * @param end
     * @return
     */
    List<ViewCountDTO> getTotalCountWithDateRange(String compaignId, String sectionId, Date start, Date end);


}
