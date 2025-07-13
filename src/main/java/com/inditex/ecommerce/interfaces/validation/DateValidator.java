package com.inditex.ecommerce.interfaces.validation;

import com.inditex.ecommerce.domain.exception.ApplicationErrorCodes;
import com.inditex.ecommerce.domain.exception.ControlledErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Slf4j
@Component
public class DateValidator {

    public LocalDateTime validateLocalDateTimeFormat(final String localDateTime) {
        try {
            return PriceDateTimeFormatter.parseStringToLocalDateTime(localDateTime);
        } catch (final DateTimeParseException e) {
            log.warn("The LocalDateTime is not in the correct format: {}. Error {}", localDateTime, e.getMessage());
            throw new ControlledErrorException(ApplicationErrorCodes.INVALID_LOCAL_DATE_TIME,
                    "The LocalDateTime is not in the correct format (yyyy-MM-dd'T'HH:mm:ss) or it does not exist");
        }
    }

}
