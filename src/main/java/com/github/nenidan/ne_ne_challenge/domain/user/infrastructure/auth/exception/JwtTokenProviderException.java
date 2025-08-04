package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.auth.exception;

import com.github.nenidan.ne_ne_challenge.global.exception.BusinessException;
import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

public class JwtTokenProviderException extends BusinessException {
    public JwtTokenProviderException(ErrorCode errorCode) {
        super(errorCode);
    }
}
