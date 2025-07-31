package com.github.nenidan.ne_ne_challenge.domain.point.exception;

import com.github.nenidan.ne_ne_challenge.global.exception.BusinessException;
import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

public class PointException extends BusinessException {
    public PointException(ErrorCode errorCode) {
        super(errorCode);
    }
}
