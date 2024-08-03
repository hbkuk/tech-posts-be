package com.techbloghub.common.exception;

import com.techbloghub.common.exception.common.ErrorCode;
import lombok.Getter;

@Getter
public class InternalServerErrorException extends RuntimeException {

    private final ErrorCode errorCode;

    public InternalServerErrorException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
