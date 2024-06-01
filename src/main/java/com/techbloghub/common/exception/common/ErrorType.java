package com.techbloghub.common.exception.common;

import lombok.Getter;

@Getter
public enum ErrorType {

    RSS_FEED_READ_FAILED(1000, "RSS Feed를 읽는데 실패했습니다."),

    REQUEST_EXCEPTION(9001, "http 요청 에러입니다."),

    INVALID_PATH(9002, "잘못된 경로입니다."),

    UNPROCESSABLE_ENTITY(9003, "요청 데이터가 유효하지 않습니다."),

    DATA_INTEGRITY_VIOLATION(9004, "예상하지 못한 데이터가 입력되었습니다."),

    NO_MORE_TRY(9005, "요청한 작업에 실패했습니다."),

    NOT_FOUND_DATA(9006, "요청한 데이터를 찾을 수 없습니다."),

    UNHANDLED_EXCEPTION(9999, "예상치 못한 예외입니다.");

    private final int code;
    private final String message;

    ErrorType(final int code, final String message) {
        this.code = code;
        this.message = message;
    }
}
