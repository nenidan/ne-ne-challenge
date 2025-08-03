package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.auth.exception;

import org.springframework.http.HttpStatus;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtTokenProviderErrorCode implements ErrorCode {

    NOT_WHITELIST("접근이 거부되었습니다.", HttpStatus.FORBIDDEN);

    private final String message;
    private final HttpStatus status;

}
