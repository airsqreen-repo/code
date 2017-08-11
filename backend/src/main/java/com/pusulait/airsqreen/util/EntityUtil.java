package com.pusulait.airsqreen.util;

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
}

