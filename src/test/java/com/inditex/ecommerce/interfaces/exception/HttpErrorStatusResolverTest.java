package com.inditex.ecommerce.interfaces.exception;

import com.inditex.ecommerce.domain.exception.ApplicationErrorCodes;
import com.inditex.ecommerce.domain.exception.BaseErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

class HttpErrorStatusResolverTest {

    static Stream<Arguments> provideErrorCodesAndStatuses() {
        return Stream.of(
                Arguments.of(ApplicationErrorCodes.NOT_FOUND_PRICE_ERROR, HttpStatus.NOT_FOUND),
                Arguments.of(ApplicationErrorCodes.INVALID_LOCAL_DATE_TIME, HttpStatus.BAD_REQUEST),
                Arguments.of((BaseErrorCode) () -> "unknown.error", HttpStatus.INTERNAL_SERVER_ERROR)
        );
    }

    @ParameterizedTest
    @MethodSource("provideErrorCodesAndStatuses")
    void resolveHttpStatusTest(BaseErrorCode errorCode, HttpStatus expectedStatus) {
        //given
        HttpErrorStatusResolver resolver = new HttpErrorStatusResolver();

        //when
        HttpStatus actualStatus = resolver.resolveHttpStatus(errorCode);

        //then
        Assertions.assertThat(actualStatus).isEqualTo(expectedStatus);
    }

}