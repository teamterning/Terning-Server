package org.terning.terningserver.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.terning.terningserver.auth.common.exception.AuthErrorCode;
import org.terning.terningserver.auth.common.exception.AuthException;
import org.terning.terningserver.common.exception.dto.ErrorResponse;
import org.terning.terningserver.common.exception.enums.ErrorMessage;
import org.terning.terningserver.auth.jwt.exception.JwtErrorCode;
import org.terning.terningserver.auth.jwt.exception.JwtException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.warn("[CustomException] cause: {}, message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
        return ResponseEntity
                .status(e.getErrorMessage().getStatus())
                .body(ErrorResponse.of(e.getErrorMessage().getStatus(), e.getErrorMessage().getMessage()));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthException e) {
        log.warn("[AuthException] cause: {}, message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
        AuthErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode.getStatus().value(), errorCode.getMessage()));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(JwtException e) {
        log.warn("[JwtException] cause: {}, message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
        JwtErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode.getStatus().value(), errorCode.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        log.warn("[Exception] cause: {} , message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
        ErrorMessage errorCode = ErrorMessage.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode.getStatus(), errorCode.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e){
        log.warn("[IlleagalArgumentException] cause: {} , message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
        ErrorMessage errorCode = ErrorMessage.ILLEGAL_ARGUMENT_ERROR;
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode.getStatus(), errorCode.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.warn("[MethodArgumentNotValidException] cause: {}, message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
        ErrorMessage errorCode = ErrorMessage.INVALID_ARGUMENT_ERROR;
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode.getStatus(), errorCode.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.warn("[HttpMessageNotReadableException] cause: {}, message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
        ErrorMessage errorCode = ErrorMessage.INVALID_FORMAT_ERROR;
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode.getStatus(), errorCode.getMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMethodException(
            HttpRequestMethodNotSupportedException e,
            HttpServletRequest request
    ) {
        log.warn("[HttpRequestMethodNotSupportedException] cause: {}, message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
        ErrorMessage errorCode = ErrorMessage.INVALID_HTTP_METHOD;
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode.getStatus(), errorCode.getMessage()));
    }
}
