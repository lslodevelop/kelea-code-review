package com.inditex.ecommerce.interfaces.validation;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@UtilityClass
public class PriceDateTimeFormatter {

    public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);

    public static LocalDateTime parseStringToLocalDateTime(final String date) throws DateTimeParseException {
        return LocalDateTime.parse(date, dateTimeFormatter);
    }
}
