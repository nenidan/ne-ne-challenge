package com.github.nenidan.ne_ne_challenge.domain.payment.exception;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum PaymentErrorCode implements ErrorCode {

    CHARGE_FAILED("포인트 결제에 실패하였습니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}
