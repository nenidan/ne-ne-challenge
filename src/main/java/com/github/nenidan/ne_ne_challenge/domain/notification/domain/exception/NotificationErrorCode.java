package com.github.nenidan.ne_ne_challenge.domain.notification.domain.exception;

import org.springframework.http.HttpStatus;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationErrorCode implements ErrorCode {

	NOT_FOUND("알림을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

	private final String message;
	private final HttpStatus status;
}