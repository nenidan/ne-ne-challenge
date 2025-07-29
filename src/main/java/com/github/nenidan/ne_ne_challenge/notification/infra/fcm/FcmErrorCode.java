package com.github.nenidan.ne_ne_challenge.notification.infra.fcm;

import org.springframework.http.HttpStatus;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FcmErrorCode implements ErrorCode {

	NOT_FOUND("토큰이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
	INTERNAL_SERVER_ERROR("서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

	private final String message;
	private final HttpStatus status;
}
