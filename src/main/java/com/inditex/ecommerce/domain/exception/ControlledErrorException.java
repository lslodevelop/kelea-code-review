package com.inditex.ecommerce.domain.exception;

public class ControlledErrorException extends BaseException {

    public ControlledErrorException(final BaseErrorCode errorCode, final String message) {
        super(errorCode, message);
    }

}
