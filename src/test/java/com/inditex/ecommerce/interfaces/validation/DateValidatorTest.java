package com.inditex.ecommerce.interfaces.validation;

import com.inditex.ecommerce.domain.exception.ControlledErrorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class DateValidatorTest {

    @InjectMocks
    private DateValidator dateValidator;

    @Test
    void validateLocalDateTimeFormatTest() {
        //given
        final String localDateTimeAsString = "2020-06-16T21:00:00";

        //when
        final LocalDateTime localDateTime = dateValidator.validateLocalDateTimeFormat(localDateTimeAsString);

        //then
        assertThat(localDateTime).isEqualTo(LocalDateTime.of(2020, 6, 16, 21, 0, 0));
    }

    @Test
    void validateLocalDateTimeFormat_nullExceptionTest() {
        //given

        //when
        final ControlledErrorException response =
                assertThrows(ControlledErrorException.class,
                        () -> dateValidator.validateLocalDateTimeFormat(null));

        //then
        assertThat(response.getMessage()).isEqualTo("The LocalDateTime cannot be null");
        assertThat(response.getErrorCode().getCode()).isEqualTo("error.ecommerce.application.02");
    }

    @Test
    void validateLocalDateTimeFormat_parseExceptionTest() {
        //given
        final String localDateTimeAsString = "test";

        //when
        final ControlledErrorException response =
                assertThrows(ControlledErrorException.class,
                        () -> dateValidator.validateLocalDateTimeFormat(localDateTimeAsString));

        //then
        assertThat(response.getMessage()).isEqualTo("The LocalDateTime is not in the correct format (yyyy-MM-dd'T'HH:mm:ss) or it does not exist");
        assertThat(response.getErrorCode().getCode()).isEqualTo("error.ecommerce.application.02");
    }

}