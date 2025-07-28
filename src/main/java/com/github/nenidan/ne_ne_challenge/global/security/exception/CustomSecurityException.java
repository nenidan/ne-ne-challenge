package com.github.nenidan.ne_ne_challenge.global.security.exception;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CustomSecurityException extends RuntimeException {

    private final transient ErrorCode errorCode;

    public CustomSecurityException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
