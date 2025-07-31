package com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.exception;

import com.github.nenidan.ne_ne_challenge.global.exception.BusinessException;
import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

public class StockException extends BusinessException {
    public StockException(ErrorCode errorCode) {
        super(errorCode);
    }
}

