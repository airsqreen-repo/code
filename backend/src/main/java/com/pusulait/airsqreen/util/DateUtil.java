package com.pusulait.airsqreen.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by benan on 7/24/2017.
 */
public class DateUtil {

    public static Date convertToDate(String dateString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            return formatter.parse(dateString);
        } catch (ParseException pe) {

        }
        return null;

    }
}
