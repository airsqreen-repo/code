package com.pusulait.airsqreen.util;

import com.pusulait.airsqreen.domain.dto.viewcount.ViewCountDTO;
import com.pusulait.airsqreen.service.viewcount.ViewCountSpendRequirement;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yildizib on 03/08/2017.
 */
public class ViewCountUtil {

    protected static ViewCountSpendRequirement viewCountSpendRequirement;

    static {

        viewCountSpendRequirement = new ViewCountSpendRequirement() {

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
     * Verilen string paramatrelerinden token uretir.
     *
     * @param items
     * @return
     */
    public static String getToken(String... items) {
        String concat = "";
        for (String s : items) {
            concat += s;
        }
        return digest("SHA-256", concat);
    }

    /**
     * @param alg
     * @param input
     * @return
     */
    public static String digest(String alg, String input) {
        try {
            MessageDigest md = MessageDigest.getInstance(alg);
            byte[] buffer = input.getBytes("UTF-8");
            md.update(buffer);
            byte[] digest = md.digest();
            return encodeHex(digest);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    /**
     *
     * @param digest
     * @return
     */
    private static String encodeHex(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
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

    /**
     * section icin detaylari icerir.
     *
     * @param campaignId
     * @param sectionId
     * @return
     */
    public static ViewCountDTO getDetail(String campaignId, String sectionId){
        return viewCountSpendRequirement.getDetail(campaignId, sectionId);
    }
}
