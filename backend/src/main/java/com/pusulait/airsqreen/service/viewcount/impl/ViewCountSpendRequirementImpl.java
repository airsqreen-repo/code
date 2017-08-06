package com.pusulait.airsqreen.service.viewcount.impl;

import com.pusulait.airsqreen.domain.dto.viewcount.ViewCountDTO;
import com.pusulait.airsqreen.service.viewcount.ViewCountSpendRequirement;
import org.springframework.stereotype.Service;

import static com.pusulait.airsqreen.util.ViewCountUtil.checkParams;

/**
 * Created by yildizib on 06/08/2017.
 */
@Service("viewCountSpendRequirement")
public class ViewCountSpendRequirementImpl implements ViewCountSpendRequirement {

    /**
     * Toplam harcama hesabinda onemlidir. Birim gosterim maliyetidir.
     * Device basinadir.
     *
     * @param campaignId
     * @param sectionId
     * @return
     * @throws NullPointerException
     */
    @Override
    public Double getUnitPrice(String campaignId, String sectionId) throws NullPointerException {
        //TODO: eger birim fiyat icin ek hesaplama gerekirse   burada degistirilecek.
        return getDetail(campaignId, sectionId).getUnitPrice();
    }

    /**
     * Kampanya section da device, action id vs bilgileri gelecek.
     * bunun icinde unitprice da var. ancak ek hesaplama varsa bir ust
     * {@link #getUnitPrice(String, String)}
     * icinde hesaplancak.
     *
     * @param campaignId
     * @param sectionId
     * @return
     * @throws NullPointerException
     */
    @Override
    public ViewCountDTO getDetail(String campaignId, String sectionId) throws NullPointerException {
        if (checkParams(campaignId, sectionId)) {
            throw new NullPointerException("campaignId, sectionId NULL veya bos olamaz!");
        }
        //TODO: buraya device id, action id vs bilgileri gelecek.
        return new ViewCountDTO();
    }
}
