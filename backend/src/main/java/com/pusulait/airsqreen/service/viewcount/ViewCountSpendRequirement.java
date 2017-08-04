package com.pusulait.airsqreen.service.viewcount;

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
}
