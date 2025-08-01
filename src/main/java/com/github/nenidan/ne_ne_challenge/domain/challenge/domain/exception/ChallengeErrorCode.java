package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ChallengeErrorCode implements ErrorCode {
    CHALLENGE_NOT_FOUND("챌린지를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    POINT_INSUFFICIENT("포인트가 부족합니다.", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus httpStatus; // Todo: 도메인에서 Http 의존성 제거 필요

    ChallengeErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }
}
