package com.techbloghub.common.exception;

import com.techbloghub.common.exception.common.ErrorCode;
import lombok.Getter;

@Getter
public class BadRequestException extends BusinessException {

    public BadRequestException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
