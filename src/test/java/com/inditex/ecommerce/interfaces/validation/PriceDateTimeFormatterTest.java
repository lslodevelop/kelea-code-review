package com.inditex.ecommerce.interfaces.validation;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PriceDateTimeFormatterTest {

    @Test
    void parseStringToLocalDateTimeTest() {
        //given
        final String dateTimeAsString = "2020-06-16T21:00:00";

        //when
        final LocalDateTime localDateTime = PriceDateTimeFormatter.parseStringToLocalDateTime(dateTimeAsString);

        //then
        assertThat(localDateTime).isEqualTo(LocalDateTime.of(2020, 6, 16, 21, 0, 0));
    }

    @Test
    void parseStringToLocalDateTime_DateTimeParseExceptionTest() {
        //given
        final String dateTimeAsString = "test";

        //when
        final DateTimeParseException dateTimeParseException = assertThrows(DateTimeParseException.class,
                () -> PriceDateTimeFormatter.parseStringToLocalDateTime(dateTimeAsString));
        //then
        assertThat(dateTimeParseException.getMessage()).isEqualTo("Text 'test' could not be parsed at index 0");
    }

}