package com.inditex.ecommerce.interfaces.handler;

import com.inditex.ecommerce.domain.exception.ApplicationErrorCodes;
import com.inditex.ecommerce.domain.exception.ControlledErrorException;
import com.inditex.ecommerce.domain.exception.GenericErrorCodes;
import com.inditex.ecommerce.interfaces.exception.HttpErrorStatusResolver;
import com.inditex.ecommerce.interfaces.model.ErrorResponseDto;
import com.inditex.ecommerce.interfaces.tracing.TraceUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private HttpErrorStatusResolver httpErrorStatusResolver;

    @Mock
    private TraceUtils traceUtils;

    @Test
    void handleControlledExceptionTest() {
        // given
        final ControlledErrorException controlledErrorException = new ControlledErrorException(
                ApplicationErrorCodes.NOT_FOUND_PRICE_ERROR,
                "Price not found"
        );

        when(traceUtils.getCurrentTraceId()).thenReturn("1234");
        when(httpErrorStatusResolver.resolveHttpStatus(ApplicationErrorCodes.NOT_FOUND_PRICE_ERROR))
                .thenReturn(HttpStatus.NOT_FOUND);

        // when
        final ResponseEntity<ErrorResponseDto> response = globalExceptionHandler.handleControlledException(controlledErrorException);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("error.ecommerce.application.01");
        assertThat(response.getBody().message()).isEqualTo("Price not found");
        verify(traceUtils, times(2)).getCurrentTraceId();
        verify(httpErrorStatusResolver).resolveHttpStatus(ApplicationErrorCodes.NOT_FOUND_PRICE_ERROR);
    }

    @Test
    void handleNoResourceFoundExceptionTest() {
        // given
        final NoResourceFoundException noResourceFoundException = new NoResourceFoundException(HttpMethod.GET, "/api/v1/prices");

        when(traceUtils.getCurrentTraceId()).thenReturn("1234");

        final HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getRequestURI()).thenReturn("/api/v1/prices");

        // when
        final ErrorResponseDto response = globalExceptionHandler.handleNoResourceFoundException(noResourceFoundException, mockRequest);

        // then
        assertThat(response.code()).isEqualTo("error.ecommerce.application.05");
        assertThat(response.message()).contains("Invalid endpoint path");
        verify(traceUtils).getCurrentTraceId();
        verify(mockRequest).getRequestURI();
    }

    @Test
    void handleMalformedUrlExceptionTest() {
        // given
        final MissingServletRequestParameterException missingServletRequestParameterException =
                new MissingServletRequestParameterException("brandId", "Long");

        when(traceUtils.getCurrentTraceId()).thenReturn("1234");

        // when
        final ErrorResponseDto response = globalExceptionHandler.handleMalformedUrlException(missingServletRequestParameterException);

        // then
        assertThat(response.code()).isEqualTo(ApplicationErrorCodes.INVALID_URL.getCode());
        assertThat(response.message()).contains("Malformed URL");
        verify(traceUtils).getCurrentTraceId();
    }

    @Test
    void handleArgumentMismatchExceptionTest() {
        // given
        final MethodArgumentTypeMismatchException methodArgumentTypeMismatchException = mock(MethodArgumentTypeMismatchException.class);
        when(methodArgumentTypeMismatchException.getMessage()).thenReturn("Expected Long but got String");

        when(traceUtils.getCurrentTraceId()).thenReturn("1234");

        // when
        final ErrorResponseDto response = globalExceptionHandler.handleArgumentMismatchException(methodArgumentTypeMismatchException);

        // then
        assertThat(response.code()).isEqualTo(ApplicationErrorCodes.MALFORMED_URL.getCode());
        assertThat(response.message()).contains("Malformed URL");
        verify(traceUtils).getCurrentTraceId();
        verify(methodArgumentTypeMismatchException, times(2)).getMessage();
    }

    @Test
    void handleBugExceptionTest() {
        // given
        final Exception exception = new IllegalStateException("Bug encountered");

        when(traceUtils.getCurrentTraceId()).thenReturn("1234");

        // when
        final ErrorResponseDto response = globalExceptionHandler.handleBugException(exception);

        // then
        assertThat(response.code()).isEqualTo(GenericErrorCodes.GENERIC_ERROR_BUG.getCode());
        assertThat(response.message()).contains("There was an unexpected error in the application");
        verify(traceUtils).getCurrentTraceId();
    }

}