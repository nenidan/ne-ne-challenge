package com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.exception;

import org.springframework.http.HttpStatus;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ReviewErrorCode implements ErrorCode {
    REVIEW_VALUE_INVALID("리뷰 점수는 1 이상 5이하여야 합니다.", HttpStatus.BAD_REQUEST),
    REVIEW_ALREADY_EXISTS("이미 리뷰가 존재합니다.", HttpStatus.CONFLICT),
    REVIEW_NOT_FOUND("리뷰 점수가 존재하지 않습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;
}