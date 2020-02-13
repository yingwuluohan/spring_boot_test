package com.unisound.iot.controller.ES;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private DateUtils(){

    }

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String HOUR_VAR = "hour";

    public static final String MINUTE_VAR = "minute";

    public static LocalDate parseDateStr(String dateStr) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static LocalDateTime parseDateStrToStartTime(String dateStr) {
        LocalDate date = parseDateStr(dateStr);
        return date.atStartOfDay();
    }

    public static LocalDateTime parseDateStrToEndTime(String dateStr) {
        LocalDate date = parseDateStr(dateStr);
        return date.plusDays(1L).atStartOfDay().minusSeconds(1L);
    }

    public static LocalDateTime parseDateTimeStr(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * eg: 2016-08-19T09:56:08.000+08:00
     */
    public static LocalDateTime parseDateTimeStrIso(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_DATE_TIME);
    }

    public static int[] split(Object timeValue) {
        int[] time = new int[2];
        if (timeValue == null || "".equals(timeValue.toString().trim())) {
            return time;
        }
        String[] timeArr = timeValue.toString().split(":");
        String hourStr = timeArr[0];
        int hour = hourStr.startsWith("0") ? Integer.parseInt(hourStr.substring(1)) : Integer.parseInt(hourStr);
        time[0] = hour;
        String minuteStr = timeArr[1];
        int minute = minuteStr.startsWith("0") ? Integer.parseInt(minuteStr.substring(1)) : Integer.parseInt(minuteStr);
        time[1] = minute;
        return time;
    }
}
