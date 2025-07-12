package com.inditex.ecommerce.interfaces.handler;


import com.inditex.ecommerce.domain.exception.ControlledErrorException;
import com.inditex.ecommerce.domain.exception.GenericErrorCodes;
import com.inditex.ecommerce.interfaces.exception.HttpErrorStatusResolver;
import com.inditex.ecommerce.interfaces.model.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;

import static java.lang.String.format;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final HttpErrorStatusResolver httpErrorStatusResolver;

    @ExceptionHandler(ControlledErrorException.class)
    public ResponseEntity<ErrorResponseDto> handleException(final ControlledErrorException ex) {
        log.warn("Controlled exception {}, with message={}. Returning response",
                ex.getErrorCode().getCode(), ex.getMessage());
        final ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getMessage(),
                ex.getErrorCode().getCode(), LocalDateTime.now());
        final HttpStatus httpStatus = httpErrorStatusResolver.resolveHttpStatus(ex.getErrorCode());
        return new ResponseEntity<>(errorResponseDto, httpStatus);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    public ErrorResponseDto handleException(final ControlledErrorException ex, final HttpServletRequest request) {
        final String message = format("The requested resource was not found: %s", request.getRequestURI());
        log.error(message);
        return new ErrorResponseDto(ex.getErrorCode().getCode(), ex.getMessage(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponseDto handleBugException(final Exception ex) {
        final String message = format
                ("There was an unexpected error in the application [BUG] %s, please contact support", ex.getMessage());
        log.error("Application error in {}, stack trace: \n %s", ex.getClass().getName(), ex);
        return new ErrorResponseDto(GenericErrorCodes.GENERIC_ERROR_BUG.getCode(), message, LocalDateTime.now());
    }

}
