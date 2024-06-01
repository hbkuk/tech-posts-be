package com.techbloghub.common.exception.server.sub;

import com.techbloghub.common.exception.common.ErrorType;
import com.techbloghub.common.exception.server.InternalServerErrorException;

public class RssFeedReadException extends InternalServerErrorException {
    public RssFeedReadException(ErrorType errorType) {
        super(errorType);
    }
}
