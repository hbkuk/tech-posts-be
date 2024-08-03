package com.techbloghub.common.exception;

import com.techbloghub.common.exception.common.ErrorCode;
import lombok.Getter;

@Getter
public class UnauthorizedException extends BusinessException {

    public UnauthorizedException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
