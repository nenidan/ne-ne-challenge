package com.github.nenidan.ne_ne_challenge.domain.payment.exception;

import com.github.nenidan.ne_ne_challenge.global.exception.BusinessException;
import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

public class PaymentException extends BusinessException {

    public PaymentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
