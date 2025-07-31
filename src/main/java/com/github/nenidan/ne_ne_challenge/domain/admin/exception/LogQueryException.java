package com.github.nenidan.ne_ne_challenge.domain.admin.exception;

import com.github.nenidan.ne_ne_challenge.global.exception.BusinessException;
import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

public class LogQueryException extends BusinessException {
    public LogQueryException(ErrorCode errorCode) {
        super(errorCode);
    }
}
