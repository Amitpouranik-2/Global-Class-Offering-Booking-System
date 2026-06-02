package com.project.Offering_Booking_System.util;

import java.time.*;
import java.time.zone.ZoneRulesException;

public final class TimeZoneUtil {

    private TimeZoneUtil() {
    }


    public static boolean isValidTimezone(String timezone) {
        try {
            ZoneId.of(timezone);
            return true;
        } catch (ZoneRulesException ex) {
            return false;
        }
    }

    public static LocalDateTime toUtc(
            LocalDateTime localDateTime,
            String timezone) {

        if (!isValidTimezone(timezone)) {
            throw new IllegalArgumentException(
                    "Invalid timezone: " + timezone);
        }

        return localDateTime
                .atZone(ZoneId.of(timezone))
                .withZoneSameInstant(ZoneOffset.UTC)
                .toLocalDateTime();
    }

    public static LocalDateTime fromUtc(LocalDateTime utcDateTime, String timezone) {

        return utcDateTime.atZone(ZoneOffset.UTC).withZoneSameInstant(
                        ZoneId.of(timezone)).toLocalDateTime();
    }

    public static LocalDateTime convert(LocalDateTime dateTime, String sourceTimezone, String targetTimezone) {

        return dateTime
                .atZone(ZoneId.of(sourceTimezone))
                .withZoneSameInstant(
                        ZoneId.of(targetTimezone))
                .toLocalDateTime();
    }

    public static ZoneId getZoneId(String timezone) {
        return ZoneId.of(timezone);
    }

    public static LocalDateTime nowUtc() {
        return LocalDateTime.now(
                ZoneOffset.UTC);
    }
}