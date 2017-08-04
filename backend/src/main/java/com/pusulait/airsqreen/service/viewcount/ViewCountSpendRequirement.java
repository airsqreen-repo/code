package com.pusulait.airsqreen.service.viewcount;

import com.pusulait.airsqreen.domain.dto.viewcount.ViewCountDTO;

/**
 * Created by yildizib on 04/08/2017.
 */
public interface ViewCountSpendRequirement {
    /**
     * Birim gosterim basina fiyat bilgisi doner...
     *
     * @param campaingId
     * @param sectionId
     * @return
     */
    Double getUnitPrice(String campaingId, String sectionId);

    /**
     * device id, action id vs bilgilerini getirir.
     *
     * @param campaignId
     * @param sectionId
     * @return
     */
    ViewCountDTO getDetail(String campaignId, String sectionId);
}
