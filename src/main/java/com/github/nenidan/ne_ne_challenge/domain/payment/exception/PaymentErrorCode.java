package com.github.nenidan.ne_ne_challenge.domain.payment.exception;

import org.springframework.http.HttpStatus;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentErrorCode implements ErrorCode {

    // 5xx
    USER_NOT_FOUND("유저를 찾을 수 없습니다.", HttpStatus.BAD_GATEWAY),
    POINT_CHARGE_FAILED("포인트 충전에 실패하였습니다.", HttpStatus.BAD_GATEWAY),

    // 4xx
    INVALID_PAYMENT_METHOD("유효하지 않은 결제 수단입니다.", HttpStatus.BAD_REQUEST),
    INVALID_PAYMENT_STATUS("유효하지 않은 결제 상태입니다.", HttpStatus.BAD_REQUEST),
    ALREADY_PROCESSED_PAYMENT("이미 처리된 결제입니다.", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus status;
}
