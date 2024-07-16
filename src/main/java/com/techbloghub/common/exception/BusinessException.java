package com.techbloghub.common.exception;

import com.techbloghub.common.exception.common.ErrorCode;
import lombok.Getter;

/**
 * Checked Exception는 BussinessException을 상속
 */
@Getter
public class BusinessException extends RuntimeException {

    final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
