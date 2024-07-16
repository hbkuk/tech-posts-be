package com.techbloghub.common.exception;

import com.techbloghub.common.exception.common.ErrorCode;
import lombok.Getter;

@Getter
public class ForbiddenException extends BusinessException {

    public ForbiddenException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
