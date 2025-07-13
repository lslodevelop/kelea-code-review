package com.inditex.ecommerce.domain.exception;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationErrorCodesTest {

    @Test
    void getCodeTest() {
        //given

        //when
        final ApplicationErrorCodes errorCodeSample = ApplicationErrorCodes.NOT_FOUND_PRICE_ERROR;

        //then
        assertThat(errorCodeSample).isNotNull();
        assertThat(errorCodeSample.getCode()).isNotEmpty();

    }

    @Test
    void valuesTest() {
        //given

        //when
        final List<ApplicationErrorCodes> applicationErrorCodes = Arrays.stream(ApplicationErrorCodes.values()).toList();

        //then
        assertThat(applicationErrorCodes).isNotNull().hasSize(5);

    }

}