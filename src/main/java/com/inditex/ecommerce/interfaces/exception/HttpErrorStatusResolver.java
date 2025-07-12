package com.inditex.ecommerce.interfaces.exception;

import com.inditex.ecommerce.domain.exception.ApplicationErrorCodes;
import com.inditex.ecommerce.domain.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class HttpErrorStatusResolver {

    // If this class becomes too big, it could be split in modules grouped by context, for instance.
    public HttpStatus resolveHttpStatus(final BaseErrorCode baseErrorCode) {
        if (baseErrorCode == ApplicationErrorCodes.NOT_FOUND_PRICE_ERROR) {
            return HttpStatus.NOT_FOUND;
        }
        if (baseErrorCode == ApplicationErrorCodes.INVALID_LOCAL_DATE_TIME) {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
