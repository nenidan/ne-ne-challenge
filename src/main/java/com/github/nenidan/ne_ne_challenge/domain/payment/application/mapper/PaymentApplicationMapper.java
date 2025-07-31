package com.github.nenidan.ne_ne_challenge.domain.payment.application.mapper;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PointClientCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.Payment;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.type.PaymentStatus;

public class PaymentApplicationMapper {

    public static PaymentResult toPaymentResult(Payment payment) {
        return new PaymentResult(
            payment.getId(),
            payment.getAmount(),
            payment.getMethod().name(),
            payment.getStatus().name(),
            payment.getStatus() == PaymentStatus.SUCCESS ? payment.getConfirmedAt() : payment.getFailedAt()
        );
    }

    public static PointClientCommand toPointClientCommand(Long userId, int amount) {
        return new PointClientCommand(
            userId,
            amount,
            "CHARGE",
            "포인트 결제"
        );
    }
}
