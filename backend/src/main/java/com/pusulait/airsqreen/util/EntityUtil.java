package com.pusulait.airsqreen.util;

import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;

public class EntityUtil {

    // Build a comma seperated value string
    public static <T> String buildString(T[] values) {

        if (values == null) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder("");

        for (T value : values) {
            stringBuilder.append(value);
            stringBuilder.append(",");
        }

        String result = stringBuilder.toString();

        if (result.length() > 0) {

            return result.substring(0, result.length() - 1);
        } else {
            return "";
        }

    }

    public static String[] buildStringArray(String values) {

        if (values == null) {
            return null;
        } else {
            return values.split(",");
        }

    }

    public static Long[] buildLongArray(String values) {

        if (values == null) {
            return null;
        } else {
            String[] strArray = values.split(",");
            Long[] longArray = new Long[strArray.length];
            for (int i = 0; i < strArray.length; i++) {
                if (!StringUtils.isEmpty(strArray[i])) {
                    longArray[i] = Long.valueOf(strArray[i]);
                }
            }
            return longArray;
        }

    }

    public static Integer evaluateUsingFrequencyInformation(Plt161Campaign plt161Campaign, Integer showPerHour) {

        if (!StringUtils.isEmpty(plt161Campaign.getFrequency_cap())) {

            if (plt161Campaign.getFrequency_cap_type().equals(Constants.FREQUENCY_HOUR) && showPerHour > plt161Campaign.getFrequency_cap()) {
                showPerHour = plt161Campaign.getFrequency_cap();
            }
            if (plt161Campaign.getFrequency_cap_type().equals(Constants.FREQUENCY_DAY) &&
                    showPerHour * EntityUtil.buildLongArray(plt161Campaign.getTargeting_hour_ids()).length > plt161Campaign.getFrequency_cap()) {
                showPerHour = plt161Campaign.getFrequency_cap() / EntityUtil.buildLongArray(plt161Campaign.getTargeting_hour_ids()).length;
            }

            if (plt161Campaign.getFrequency_cap_type().equals(Constants.FREQUENCY_WEEK) &&
                    showPerHour * EntityUtil.buildLongArray(plt161Campaign.getTargeting_hour_ids()).length * EntityUtil.buildLongArray(plt161Campaign.getTargeting_weekday_ids()).length
                            > plt161Campaign.getFrequency_cap()) {
                showPerHour = plt161Campaign.getFrequency_cap() /
                        (EntityUtil.buildLongArray(plt161Campaign.getTargeting_hour_ids()).length * EntityUtil.buildLongArray(plt161Campaign.getTargeting_weekday_ids()).length);
            }
        }
        return showPerHour;
    }
}

