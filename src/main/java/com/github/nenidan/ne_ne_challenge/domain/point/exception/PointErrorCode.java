package com.github.nenidan.ne_ne_challenge.domain.point.exception;

import org.springframework.http.HttpStatus;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointErrorCode implements ErrorCode{
    INSUFFICIENT_BALANCE("포인트가 부족합니다.", HttpStatus.BAD_REQUEST),
    POINT_WALLET_NOT_FOUND("포인트 지갑이 존재하지 않습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;
}
