package com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.exception;

import org.springframework.http.HttpStatus;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderErrorCode implements ErrorCode {
    ORDER_NOT_FOUND("주문 내역을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ORDER_ALREADY_CANCELED("이미 취소된 주문입니다.", HttpStatus.CONFLICT),
    ORDER_DETAIL_NOT_FOUND("주문 상세 내역을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ORDER_NOT_CANCELED_FOR_RECOVERY("복구하려는 주문이 취소 상태가 아닙니다.", HttpStatus.CONFLICT),
    ORDER_FAILED_CANCEL("주문 취소에 실패하였습니다.", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus status;
}