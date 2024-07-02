package org.ftc.fileprocessing.platform.time;

import java.time.ZonedDateTime;

public class TimeUtils {

    static public String toStringAsLocalDateTime(ZonedDateTime zonedDateTime) {
        return String.format("%s, %s", toStringAsLocalDate(zonedDateTime), toStringAsLocalTime(zonedDateTime));
    }

    static public String toStringAsLocalTime(ZonedDateTime zonedDateTime) {
        var localTime = zonedDateTime.toLocalTime();

        StringBuilder buf = new StringBuilder(18);
        int hourValue = localTime.getHour();
        int minuteValue = localTime.getMinute();

        buf.append(hourValue < 10 ? "0" : "").append(hourValue)
                .append(minuteValue < 10 ? ":0" : ":").append(minuteValue);
        return buf.toString();
    }

    static public String toStringAsLocalDate(ZonedDateTime zonedDateTime) {
        var localDate = zonedDateTime.toLocalDate();
        int yearValue = localDate.getYear();
        int monthValue = localDate.getMonthValue();
        int dayValue = localDate.getDayOfMonth();
        int absYear = Math.abs(yearValue);
        StringBuilder buf = new StringBuilder(10);
        buf.append(dayValue < 10 ? "0" : "")
                .append(dayValue)
                .append(monthValue < 10 ? ".0" : ".")
                .append(monthValue)
                .append(".");

        if (absYear < 1000) {
            if (yearValue < 0) {
                buf.append(yearValue - 10000).deleteCharAt(1);
            } else {
                buf.append(yearValue + 10000).deleteCharAt(0);
            }
        } else {
            if (yearValue > 9999) {
                buf.append('+');
            }
            buf.append(yearValue);
        }

        return buf.toString();
    }
}
