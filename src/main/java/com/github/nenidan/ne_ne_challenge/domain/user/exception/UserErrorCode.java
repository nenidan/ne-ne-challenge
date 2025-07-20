package com.github.nenidan.ne_ne_challenge.domain.user.exception;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    INVALID_USER_ROLE("유효하지 않은 사용자 역할입니다.", HttpStatus.BAD_REQUEST),
    DUPLICATE_EMAIL("이미 존재하는 이메일입니다.", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus status;

}
