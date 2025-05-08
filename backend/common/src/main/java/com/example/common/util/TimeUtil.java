package com.example.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @Description： Date Formatter Util.
 * @Author： RainbowJier
 * @Data： 2024/8/12 21:58
 */
public class TimeUtil {
    /**
     * Default date time format.
     */
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_PATTERN);
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    /**
     * Convert LocalDateTime to String.
     */
    public static String format(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(localDateTime.atZone(DEFAULT_ZONE_ID));
    }

    /**
     * Convert Date to String with custom pattern
     */
    public static String format(Date time, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(time.toInstant().atZone(DEFAULT_ZONE_ID));
    }

    /**
     * Convert Date to String.
     */
    public static String format(Date time) {
        return DEFAULT_DATE_TIME_FORMATTER.format(time.toInstant().atZone(DEFAULT_ZONE_ID));
    }

    /**
     * Convert Timestamp to String.
     */
    public static String format(long timestamp) {
        return DEFAULT_DATE_TIME_FORMATTER.format(new Date(timestamp).toInstant().atZone(DEFAULT_ZONE_ID));
    }

    /**
     * Convert String to Date.
     */
    public static Date strToDate(String time) {
        LocalDateTime localDateTime = LocalDateTime.parse(time, DEFAULT_DATE_TIME_FORMATTER);
        return Date.from(localDateTime.atZone(DEFAULT_ZONE_ID).toInstant());
    }

    /**
     * Get the remaining seconds of the day,
     * to check whether the traffic package is expired or not.
     */
    public static Integer getRemainSecondsOneDay(Date currentDate) {
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return (int) seconds;
    }
}
