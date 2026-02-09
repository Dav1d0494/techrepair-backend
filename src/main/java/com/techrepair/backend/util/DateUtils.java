package com.techrepair.backend.util;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static String formatDate(Instant i) {
        if (i == null) return null;
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault()).format(i);
    }

    public static Instant parseDate(String s) {
        return Instant.parse(s);
    }

    public static long getDaysBetween(Instant a, Instant b) {
        return Duration.between(a, b).toDays();
    }
}
