package com.techbloghub.common.exception.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    RSS_FEED_READ_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 1000, "RSS Feed를 읽는데 실패했습니다."),

    INVALID_PATH(HttpStatus.BAD_REQUEST, 9002, "잘못된 경로입니다."),
    UNPROCESSABLE_ENTITY(HttpStatus.BAD_REQUEST, 9003, "요청 데이터가 유효하지 않습니다."),
    NOT_FOUND_DATA(HttpStatus.BAD_REQUEST, 9006, "요청한 데이터를 찾을 수 없습니다."),

    NOT_SUPPORTED_OAUTH_SERVICE(HttpStatus.BAD_REQUEST, 9100, "해당 OAuth를 제공하지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, 9101, "올바르지 않은 형식의 RefreshToken입니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, 9102, "올바르지 않은 형식의 AccessToken입니다."),

    EXPIRED_PERIOD_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, 9103, "기한이 만료된 RefreshToken입니다."),
    EXPIRED_PERIOD_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, 9104, "기한이 만료된 AccessToken입니다."),

    INVALID_REQUEST_BODY(HttpStatus.BAD_REQUEST, 9104, "올바르지 않은 요청 데이터입니다."),
    NOT_FOUND_COOKIE(HttpStatus.BAD_REQUEST, 9105, "쿠키 정보를 찾을 수 없습니다."),

    UNHANDLED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, 9999, "예상치 못한 예외입니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, final int code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
