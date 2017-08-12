package com.pusulait.airsqreen.util;

import com.pusulait.airsqreen.domain.campaign.platform161.Plt161Campaign;

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
        int dayOfWeekInJava = cal.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeekInJava > 1){
            return dayOfWeekInJava - 1;
        }
        if (dayOfWeekInJava  == 1)
            return 7;
        else return 0;

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

    public static boolean isInWeekDay(Plt161Campaign plt161Campaign, Date date) {

        Long[] weekDayIds = EntityUtil.buildLongArray(plt161Campaign.getTargeting_weekday_ids());

        boolean inWeekDay = false;

        if (StringUtils.isEmpty(plt161Campaign.getTargeting_weekday_ids())) {
            inWeekDay = true;
        } else {
            for (Long weekDayId : weekDayIds) {
                if (weekDayId.intValue() == DateUtil.getDayOfWeek(date)) {
                    inWeekDay = true;
                }
            }
        }
        return inWeekDay;
    }

    public static boolean isInDayHour(Plt161Campaign plt161Campaign, Date date) {

        Long[] dayHourIds = EntityUtil.buildLongArray(plt161Campaign.getTargeting_hour_ids());

        boolean inDayHour = false;

        if (StringUtils.isEmpty(plt161Campaign.getTargeting_hour_ids())) {

            inDayHour = true;
        } else {
            for (Long dayHourId : dayHourIds) {
                if (dayHourId.intValue() == DateUtil.getHourOfDate(date)) {
                    inDayHour = true;
                }
            }
        }
        return inDayHour;
    }

    public static Integer calculateHour(Plt161Campaign plt161Campaign, Date date, Boolean isLastDay) {

        if (!isInDayHour(plt161Campaign, date)) {
            return 0;
        }

        Integer campaignEndHour = DateUtil.getHourOfDate(plt161Campaign.getEndOn());
        Integer hour = DateUtil.getHourOfDate(new Date());
        Long[] dayHourIds = EntityUtil.buildLongArray(plt161Campaign.getTargeting_hour_ids());
        int workableHourCount = 0;

        for (Long dayHourId : dayHourIds) {
            if (hour < dayHourId && !isLastDay) {
                workableHourCount++;
            } else if (campaignEndHour > dayHourId && hour < dayHourId) {
                workableHourCount++;
            }
        }
        return workableHourCount;
    }


}
