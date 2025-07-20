package com.github.nenidan.ne_ne_challenge.global.security.exception;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;

@Getter
public enum SecurityServletErrorCode {

    // JWT 관련 오류
    INVALID_JWT_SIGNATURE(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않는 JWT 서명입니다."),
    EXPIRED_JWT_TOKEN(HttpServletResponse.SC_UNAUTHORIZED, "만료된 JWT 토큰입니다."),
    UNSUPPORTED_JWT(HttpServletResponse.SC_BAD_REQUEST, "지원되지 않는 JWT 토큰입니다."),
    INVALID_JWT(HttpServletResponse.SC_BAD_REQUEST, "잘못된 JWT 토큰입니다."),

    // Security 관련 오류
    UNAUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, "인증이 필요합니다."),
    ACCESS_DENIED(HttpServletResponse.SC_FORBIDDEN, "접근이 거부되었습니다."),

    // 내부 서버 오류
    INTERNAL_SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "내부 서버 오류입니다.");

    private final int httpStatus;
    private final String message;

    SecurityServletErrorCode(int httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
