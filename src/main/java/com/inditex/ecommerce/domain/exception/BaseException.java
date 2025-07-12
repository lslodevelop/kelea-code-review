package com.inditex.ecommerce.domain.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseException extends RuntimeException{

    private final transient BaseErrorCode errorCode;

    public BaseException(final BaseErrorCode baseErrorCode, final String message) {
        super(message);
        this.errorCode = baseErrorCode;
    }

}
