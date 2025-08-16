package com.github.nenidan.ne_ne_challenge.domain.payment.exception;

import org.springframework.http.HttpStatus;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentErrorCode implements ErrorCode {

    // 5xx
    USER_NOT_FOUND("유저를 찾을 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    POINT_CHARGE_FAILED("포인트 충전에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    CONFIRM_FAILED("토스 결제를 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    PAYMENT_PROCESSING_FAILED("결제 처리 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    EXTERNAL_SERVICE_ERROR("외부 서비스 연동 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // 4xx
    INVALID_PAYMENT_METHOD("유효하지 않은 결제 수단입니다.", HttpStatus.BAD_REQUEST),
    INVALID_PAYMENT_STATUS("유효하지 않은 결제 상태입니다.", HttpStatus.BAD_REQUEST),
    ALREADY_PROCESSED_PAYMENT("이미 처리된 결제입니다.", HttpStatus.CONFLICT),
    AMOUNT_MISMATCH("결제 금액이 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    PAYMENT_NOT_FOUND("결제 내역을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    TOSS_ERROR("토스 결제에 실패하였습니다.", HttpStatus.BAD_REQUEST),
    CANNOT_CANCEL_PAYMENT("취소할 수 없는 결제입니다.", HttpStatus.BAD_REQUEST),
    CANNOT_CANCEL_EXPIRED("취소 가능 기간이 지났습니다.", HttpStatus.BAD_REQUEST),
    PAYMENT_ACCESS_DENIED("본인의 결제만 취소할 수 있습니다.", HttpStatus.FORBIDDEN),
    INVALID_PAYMENT_AMOUNT("유효하지 않은 요청 금액입니다.", HttpStatus.BAD_REQUEST),
    PAYMENT_CANCEL_FAILED("결제 취소 중 오류가 발생하였습니다.", HttpStatus.BAD_REQUEST),
    INVALID_AMOUNT("금액은 0원 이상이어야 합니다.", HttpStatus.BAD_REQUEST),
    INVALID_ORDER_ID("유효하지 않은 주문 ID입니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}
