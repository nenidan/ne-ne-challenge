package com.github.nenidan.ne_ne_challenge.domain.point.exception;

import org.springframework.http.HttpStatus;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointErrorCode implements ErrorCode{

    // 4xx
    INSUFFICIENT_POINT_BALANCE("포인트가 부족합니다.", HttpStatus.BAD_REQUEST),
    POINT_WALLET_NOT_FOUND("포인트 지갑이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    WALLET_ALREADY_EXISTS("이미 존재하는 포인트지갑입니다.", HttpStatus.CONFLICT),
    INVALID_POINT_REASON("유효하지 않은 포인트 증감 사유입니다.", HttpStatus.BAD_REQUEST),
    INVALID_INCREASE_AMOUNT("증가할 포인트는 0보다 커야 합니다", HttpStatus.BAD_REQUEST),
    ALREADY_CANCELED_TRANSACTION("이미 취소된 포인트 내역입니다.", HttpStatus.CONFLICT),
    CANNOT_CANCEL_USED_POINTS("한번 사용한 포인트는 취소 할 수 없습니다.", HttpStatus.CONFLICT),
    POINT_NOT_FOUND("해당 주문번호로 생성된 포인트 내역이 존재하지 않습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;
}
