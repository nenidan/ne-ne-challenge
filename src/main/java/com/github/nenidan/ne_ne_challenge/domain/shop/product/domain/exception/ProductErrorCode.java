package com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.exception;

import org.springframework.http.HttpStatus;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProductErrorCode implements ErrorCode {
    PRODUCT_NOT_FOUND("상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    PRODUCT_ALREADY_DELETED("이미 삭제된 상품입니다.", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus status;
}