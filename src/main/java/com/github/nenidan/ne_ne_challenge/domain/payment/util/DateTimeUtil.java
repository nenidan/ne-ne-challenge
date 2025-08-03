package com.github.nenidan.ne_ne_challenge.domain.payment.util;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class DateTimeUtil {

    public static LocalDateTime parseToLocalDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
            return null;
        }

        return OffsetDateTime.parse(dateTimeStr)
            .toLocalDateTime();
    }
}
