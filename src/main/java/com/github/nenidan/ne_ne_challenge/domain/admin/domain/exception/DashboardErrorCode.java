package com.github.nenidan.ne_ne_challenge.domain.admin.domain.exception;

import org.springframework.http.HttpStatus;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DashboardErrorCode implements ErrorCode {

    INVALID_LOG_TYPE("유효하지 않는 도메인 타입입니다.", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("서버 내부 오류입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCESS_DENIED("인가되지 않은 접근입니다.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN_OPERATION("허용되지 않은 작업입니다.", HttpStatus.FORBIDDEN),
    RESOURCE_NOT_FOUND("요청한 리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    METHOD_NOT_ALLOWED("허용되지 않은 HTTP 메서드입니다.", HttpStatus.METHOD_NOT_ALLOWED),

    LOGGING_FAILURE("로그 기록 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;

}
