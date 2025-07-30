package com.github.nenidan.ne_ne_challenge.domain.shop.exception;

import com.github.nenidan.ne_ne_challenge.global.exception.BusinessException;
import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

public class ShopException extends BusinessException {
    public ShopException(ErrorCode errorCode) {
        super(errorCode);
    }

    public PaymentException(ErrorCode errorCode, Exception e) {
        super(errorCode, e);
    }
}

