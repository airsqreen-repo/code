package com.pusulait.airsqreen.util;

import com.pusulait.airsqreen.service.viewcount.ViewCountSpendRequirement;

/**
 * Created by yildizib on 03/08/2017.
 */
public class ViewCountUtil {

    protected static ViewCountSpendRequirement viewCountSpendRequirement;

    static {

        viewCountSpendRequirement = new ViewCountSpendRequirement() {
            @Override
            public Double getUnitPrice(String campaignId, String sectionId) throws NullPointerException {
                if (checkParams(campaignId, sectionId)) {
                    throw new NullPointerException("campaignId, sectionId NULL veya bos olamaz!");
                }
                //TODO: buraya birim fiyat gelecek device basina o da sectionId den bulunacak.
                return 1D;
            }
        };

    }

    /**
     * @param o
     * @return
     */
    public static Boolean isEmpty(Object o) {
        Boolean result = false;
        if (o == null) {
            result = true;
        }
        if (o instanceof String) {
            if (o.equals("")) {
                result = true;
            }
        }
        return result;
    }

    /**
     * @param objects
     * @return
     */
    public static Boolean checkParams(Object... objects) {
        Boolean result = false;
        for (Object o : objects) {
            if (isEmpty(o)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Birim basina donecek fiyat...
     *
     * @param campaignId
     * @param sectionId
     * @return
     */
    public static Double getUnitPrice(String campaignId, String sectionId) {
        return viewCountSpendRequirement.getUnitPrice(campaignId, sectionId);
    }
}
