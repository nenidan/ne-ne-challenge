package com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.exception;

import org.springframework.http.HttpStatus;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StockErrorCode implements ErrorCode {
    STOCK_NOT_FOUND("재고를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    STOCK_NOT_EMPTY("재고가 비어있지 않습니다. ", HttpStatus.CONFLICT),
    OUT_OF_STOCK("재고가 부족합니다.", HttpStatus.BAD_REQUEST),
    INVALID_INBOUND_QUANTITY("입고 수량은 0 이하일 수 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_OUTBOUND_QUANTITY("출고 수량은 0 이하일 수 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_ENOUGH_OUTBOUND_QUANTITY("현재 수량보다 출고 수량이 많을 수는 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_ENOUGH_DECREASE_QUANTITY("현재 수량보다 재고 감소 수량이 많을 수는 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_DECREASE_QUANTITY("재고 감소 수량은 0 이하일 수 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_RESTORE_QUANTITY("복구 재고 수량은 0 이하일 수 없습니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}