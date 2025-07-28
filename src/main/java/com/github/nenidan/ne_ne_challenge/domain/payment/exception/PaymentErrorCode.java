package com.github.nenidan.ne_ne_challenge.domain.payment.exception;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum PaymentErrorCode implements ErrorCode {
    ;

    private final String message;
    private final HttpStatus status;
}
