package com.github.nenidan.ne_ne_challenge.global.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // 부모 클래스에 메시지 전달
        this.errorCode = errorCode;
    }
}