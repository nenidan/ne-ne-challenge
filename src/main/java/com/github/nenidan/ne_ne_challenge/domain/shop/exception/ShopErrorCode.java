package com.github.nenidan.ne_ne_challenge.domain.shop.exception;

import org.springframework.http.HttpStatus;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ShopErrorCode implements ErrorCode {
    PRODUCT_NOT_FOUND("상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    PRODUCT_ALREADY_DELETED("이미 삭제된 상품입니다.", HttpStatus.CONFLICT),
    ORDER_NOT_FOUND("주문 내역을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ORDER_ALREADY_CANCELED("이미 취소된 주문입니다.", HttpStatus.CONFLICT),
    ORDER_DETAIL_NOT_FOUND("주문 상세 내역을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    USER_NOT_FOUND("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;
}