package com.techbloghub.common.exception.badRequest.sub;

import com.techbloghub.common.exception.badRequest.BadRequestException;
import com.techbloghub.common.exception.common.ErrorType;

public class NotFoundDataException extends BadRequestException {
    public NotFoundDataException(ErrorType errorType) {
        super(errorType);
    }
}
