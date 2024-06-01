package com.techbloghub.common.exception.server;

import com.techbloghub.common.exception.common.ErrorType;
import lombok.Getter;

@Getter
public class InternalServerErrorException extends RuntimeException {

    private final int code;

    public InternalServerErrorException(final ErrorType errorType) {
        super(errorType.getMessage());
        this.code = errorType.getCode();
    }
}
