package com.inditex.ecommerce.interfaces.handler;


import com.inditex.ecommerce.domain.exception.ApplicationErrorCodes;
import com.inditex.ecommerce.domain.exception.ControlledErrorException;
import com.inditex.ecommerce.domain.exception.GenericErrorCodes;
import com.inditex.ecommerce.interfaces.exception.HttpErrorStatusResolver;
import com.inditex.ecommerce.interfaces.model.ErrorResponseDto;
import com.inditex.ecommerce.interfaces.tracing.TraceUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;

import static java.lang.String.format;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final HttpErrorStatusResolver httpErrorStatusResolver;
    private final TraceUtils traceUtils;

    @ExceptionHandler(ControlledErrorException.class)
    public ResponseEntity<ErrorResponseDto> handleControlledException(final ControlledErrorException ex) {
        log.warn("Controlled exception {}, with message={}. Trace ID: {}. Returning response",
                ex.getErrorCode().getCode(), ex.getMessage(), traceUtils.getCurrentTraceId());
        final ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getErrorCode().getCode(),
                ex.getMessage(), traceUtils.getCurrentTraceId(), LocalDateTime.now());
        final HttpStatus httpStatus = httpErrorStatusResolver.resolveHttpStatus(ex.getErrorCode());
        return new ResponseEntity<>(errorResponseDto, httpStatus);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    public ErrorResponseDto handleNoResourceFoundException(final NoResourceFoundException ex, final HttpServletRequest request) {
        final String message = format("Invalid endpoint path: %s. Review the path", request.getRequestURI());
        log.error(message);
        return new ErrorResponseDto(ApplicationErrorCodes.WRONG_PATH.getCode(), message,
                traceUtils.getCurrentTraceId(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorResponseDto handleMalformedUrlException(final Exception ex) {
        final String message = format
                ("Malformed URL. %s:", ex.getMessage());
        log.error("Invalid URL. Please check proper format {}", ex.getMessage());
        return new ErrorResponseDto(ApplicationErrorCodes.INVALID_URL.getCode(), message,
                traceUtils.getCurrentTraceId() ,LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponseDto handleArgumentMismatchException(final Exception ex) {
        final String message = format
                ("Malformed URL. %s:", ex.getMessage());
        log.error("Invalid parameter types for provided URL. Please check proper format {}", ex.getMessage());
        return new ErrorResponseDto(ApplicationErrorCodes.MALFORMED_URL.getCode(), message,
                traceUtils.getCurrentTraceId(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponseDto handleBugException(final Exception ex) {
        final String message = format
                ("There was an unexpected error in the application [BUG]: %s, please contact support", ex.getMessage());
        log.error("Application error in {}, stack trace: \n %s", ex.getClass().getName(), ex);
        return new ErrorResponseDto(GenericErrorCodes.GENERIC_ERROR_BUG.getCode(), message,
                traceUtils.getCurrentTraceId(), LocalDateTime.now());
    }

}
