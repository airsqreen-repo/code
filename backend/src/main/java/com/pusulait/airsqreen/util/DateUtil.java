package com.pusulait.airsqreen.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public static Integer getDayOfWeek() {

        Calendar c1 = Calendar.getInstance();

        return c1.get(Calendar.DAY_OF_WEEK);

    }

    public static Integer getDayOfWeek(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);

    }

    public static Integer getHourOfDate(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);

    }

    public static Boolean isInSameDay(Date date1, Date date2) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        return cal.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

    }


}
