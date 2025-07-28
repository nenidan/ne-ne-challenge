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
    STOCK_NOT_FOUND("재고를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    OUT_OF_STOCK("재고가 부족합니다.", HttpStatus.BAD_REQUEST),
    INVALID_DECREASE_QUANTITY("재고 감소에 음수가 들어올 수 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_ACTION("지원하지 않는 기능입니다.",  HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}