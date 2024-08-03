package com.techbloghub.common.exception.common.dto;

import com.techbloghub.common.exception.common.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@AllArgsConstructor
public class ErrorResponse {

    @Schema(description = "에러 코드", example = "9999")
    private int errorCode;

    @Schema(description = "에러 메시지", example = "요청 파라미터 누락")
    private String message;

    @Schema(description = "HTTP 상태", example = "INTERNAL_SERVER_ERROR")
    private HttpStatus httpStatus;

    @Schema(description = "검증을 실패한 필드")
    private List<FieldError> errors = new ArrayList<>();

    private ErrorResponse(final ErrorCode errorCode) {
        this.errorCode = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.httpStatus = errorCode.getHttpStatus();
    }

    private ErrorResponse(final ErrorCode code, final List<FieldError> errors) {
        this.errorCode = code.getCode();
        this.message = code.getMessage();
        this.httpStatus = code.getHttpStatus();
        this.errors = errors;
    }

    public static ErrorResponse from(final ErrorCode e) {
        return new ErrorResponse(e);
    }

    public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
        return new ErrorResponse(code, FieldError.of(bindingResult));
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    static class FieldError {

        private String field;
        private String value;
        private String reason;

        public static List<FieldError> of(final String field, final String value,
            final String reason) {
            return List.of(new FieldError(field, value, reason));
        }

        private static List<FieldError> of(final BindingResult result) {
            return result.getAllErrors().stream().map(error -> {
                var field = (org.springframework.validation.FieldError) error;

                return FieldError.builder()
                    .field(field.getField())
                    .reason(field.getDefaultMessage())
                    .value(
                        field.getRejectedValue() == null ? "" : field.getRejectedValue().toString())
                    .build();
            }).collect(Collectors.toList());
        }
    }
}
