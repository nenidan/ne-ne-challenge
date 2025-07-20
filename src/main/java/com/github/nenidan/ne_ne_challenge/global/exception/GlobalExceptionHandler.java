package com.github.nenidan.ne_ne_challenge.global.exception;

import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.security.exception.CustomSecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. 비즈니스 로직에서 발생한 커스텀 예외 처리
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        return ApiResponse.error(e.getErrorCode().getStatus(), e.getMessage());
    }

    @ExceptionHandler(CustomSecurityException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomSecurityException(CustomSecurityException e) {
        return ApiResponse.error(e.getErrorCode().getStatus(), e.getMessage());
    }

    // 2. 그 외 모든 예외 처리 (최후의 보루)
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error(e.getMessage());
        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 오류가 발생했습니다.");
    }
}