package com.inditex.ecommerce.domain.exception;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GenericErrorCodesTest {

    @Test
    void getCodeTest() {
        //given

        //when
        final GenericErrorCodes errorCodeSample = GenericErrorCodes.GENERIC_ERROR_BUG;

        //then
        assertThat(errorCodeSample).isNotNull();
        assertThat(errorCodeSample.getCode()).isNotEmpty();

    }

    @Test
    void valuesTest() {
        //given

        //when
        final List<GenericErrorCodes> genericErrorCodes = Arrays.stream(GenericErrorCodes.values()).toList();

        //then
        assertThat(genericErrorCodes).isNotNull().hasSize(1);

    }

}