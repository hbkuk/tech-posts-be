package com.techbloghub.common.exception;

import static com.techbloghub.common.exception.common.ErrorCode.NOT_FOUND_COOKIE;
import static com.techbloghub.common.exception.common.ErrorCode.UNHANDLED_EXCEPTION;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.techbloghub.common.alert.SlackLogger;
import com.techbloghub.common.exception.common.dto.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;


@Slf4j
@RestControllerAdvice
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionAdvice {

    private BodyBuilder baseHandler(BusinessException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler({
        BadRequestException.class,
        UnauthorizedException.class,
        ForbiddenException.class,
        UnsupportedOAuthTypeException.class
    })
    public ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
        log.warn("BusinessException", e);

        return baseHandler(e).body(ErrorResponse.from(e.getErrorCode()));
    }

    @ExceptionHandler(MissingRequestCookieException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestCookieException(
        MissingRequestCookieException e) {
        log.warn("MissingRequestCookieException", e);

        return ResponseEntity.status(e.getStatusCode()).body(ErrorResponse.from(NOT_FOUND_COOKIE));
    }

    @SlackLogger
    @ExceptionHandler({
        HttpRequestMethodNotSupportedException.class,
        HttpMediaTypeNotSupportedException.class,
        HttpMediaTypeNotAcceptableException.class,
        MissingPathVariableException.class,
        MissingServletRequestParameterException.class,
        MissingServletRequestPartException.class,
        ServletRequestBindingException.class,
        MethodArgumentNotValidException.class,
        NoHandlerFoundException.class,
        AsyncRequestTimeoutException.class,
        MaxUploadSizeExceededException.class,
        ConversionNotSupportedException.class,
        TypeMismatchException.class,
        HttpMessageNotReadableException.class,
        HttpMessageNotWritableException.class,
        BindException.class
    })
    public ResponseEntity<ErrorResponse> handleCommonExceptions(Exception e) {
        log.warn("Common Exception", e);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.from(UNHANDLED_EXCEPTION));
    }

    @SlackLogger
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerException(
        final InternalServerErrorException e) {
        log.warn("InternalServerError Exception", e);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.from(e.getErrorCode()));
    }

    @SlackLogger
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> unHandledExceptionHandler(final Exception e) {
        log.error("Not Expected Exception is Occurred", e);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.from(UNHANDLED_EXCEPTION));
    }

}


