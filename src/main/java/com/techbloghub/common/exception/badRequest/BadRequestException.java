package com.techbloghub.common.exception.badRequest;

import com.techbloghub.common.exception.common.ErrorType;
import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private final int code;

    public BadRequestException(final ErrorType errorType) {
        super(errorType.getMessage());
        this.code = errorType.getCode();
    }
}
