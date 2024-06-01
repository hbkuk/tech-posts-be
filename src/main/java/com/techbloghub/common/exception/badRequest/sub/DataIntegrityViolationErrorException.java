package com.techbloghub.common.exception.badRequest.sub;

import com.techbloghub.common.exception.common.ErrorType;
import com.techbloghub.common.exception.server.InternalServerErrorException;

public class DataIntegrityViolationErrorException extends InternalServerErrorException {
    private ErrorType errorType;

    public DataIntegrityViolationErrorException(ErrorType errorType) {
        super(errorType);
    }
}
