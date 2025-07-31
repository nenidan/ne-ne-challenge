package com.github.nenidan.ne_ne_challenge.domain.payment.application.mapper;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentConfirmResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentPrepareResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.Payment;

public class PaymentApplicationMapper {

    public static PaymentResult toPaymentResult(Payment payment) {
        return new PaymentResult(
            payment.getId(),
            payment.getOrderId(),
            payment.getAmount(),
            payment.getOrderName(),
            payment.getStatus().name(),
            payment.getPaymentMethod(),
            payment.getApprovedAt(),
            payment.getFailedAt()
        );
    }

    public static PaymentPrepareResult toPaymentPrepareResult(Payment payment) {
        return new PaymentPrepareResult(
            payment.getAmount(),
            payment.getOrderId(),
            payment.getOrderName()
        );
    }

    public static PaymentConfirmResult toPaymentConfirmResult(Payment payment) {
        return new PaymentConfirmResult(
            payment.getOrderId(),
            payment.getAmount(),
            payment.getPaymentMethod(),
            payment.getPaymentKey(),
            payment.getOrderName(),
            payment.getApprovedAt()
        );
    }
}
