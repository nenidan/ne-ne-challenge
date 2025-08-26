package com.github.nenidan.ne_ne_challenge.global.exception;

// Todo: 도메인에서 Http 의존성 제거 필요
import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getMessage(); // 에러 메시지
    HttpStatus getStatus(); // HTTP 상태코드
}