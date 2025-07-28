package com.github.nenidan.ne_ne_challenge.global.security.exception;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SecurityErrorCode implements ErrorCode {    // Security 관련 에러 코드 - 내부 예외 발생용

    TOKEN_NOT_FOUND("토큰을 찾을 수 없습니다.", HttpStatus.UNAUTHORIZED);

    private final String message;
    private final HttpStatus status;

}
