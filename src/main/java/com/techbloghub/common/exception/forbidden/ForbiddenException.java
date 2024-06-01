package com.techbloghub.common.exception.forbidden;

import com.techbloghub.common.exception.common.ErrorType;
import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {

    private final int code;

    public ForbiddenException(final ErrorType errorType) {
        super(errorType.getMessage());
        this.code = errorType.getCode();
    }
}
