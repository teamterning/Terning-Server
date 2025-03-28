package org.terning.terningserver.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.terning.terningserver.auth.common.exception.AuthErrorCode;
import org.terning.terningserver.auth.common.exception.AuthException;
import org.terning.terningserver.jwt.exception.JwtErrorCode;
import org.terning.terningserver.jwt.exception.JwtException;
import org.terning.terningserver.exception.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.of(
                        e.getErrorMessage().getStatus(),
                        e.getErrorMessage().getMessage())
                );
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(JwtException e) {
        JwtErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode.getStatus().value(), errorCode.getMessage()));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthException e) {
        AuthErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode.getStatus().value(), errorCode.getMessage()));
    }
}

