package com.techbloghub.core.rss.exception;

import com.techbloghub.common.exception.InternalServerErrorException;
import com.techbloghub.common.exception.common.ErrorCode;

public class RssFeedReadException extends InternalServerErrorException {

    public RssFeedReadException(ErrorCode errorCode) {
        super(errorCode);
    }
}
