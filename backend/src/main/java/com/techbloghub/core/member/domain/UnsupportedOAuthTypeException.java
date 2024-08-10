package com.techbloghub.core.member.domain;

import com.techbloghub.common.exception.BusinessException;
import com.techbloghub.common.exception.common.ErrorCode;
import lombok.Getter;

@Getter
public class UnsupportedOAuthTypeException extends BusinessException {
    
    public UnsupportedOAuthTypeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
