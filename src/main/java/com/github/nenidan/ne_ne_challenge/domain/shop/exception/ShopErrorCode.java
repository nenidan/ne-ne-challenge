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
    INVALID_DECREASE_QUANTITY("재고 감소에 음수 또는 0이 들어올 수 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_ACTION("지원하지 않는 기능입니다.",  HttpStatus.BAD_REQUEST),
    REVIEW_VALUE_INVALID("리뷰 점수는 1 이상 5이하여야 합니다.", HttpStatus.BAD_REQUEST),
    REVIEW_ALREADY_EXISTS("이미 리뷰가 존재합니다.", HttpStatus.CONFLICT),
    REVIEW_NOT_FOUND("리뷰 점수가 존재하지 않습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;
}