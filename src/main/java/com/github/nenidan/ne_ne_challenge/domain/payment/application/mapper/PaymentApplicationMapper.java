package com.github.nenidan.ne_ne_challenge.domain.payment.application.mapper;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentCancelResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentConfirmResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentSearchResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.Payment;

public class PaymentApplicationMapper {

    public static PaymentSearchResult toPaymentResult(Payment payment) {
        return new PaymentSearchResult(
            payment.getId(),
            payment.getOrderId(),
            payment.getAmount(),
            payment.getStatus().name(),
            payment.getPaymentMethod(),
            payment.getApprovedAt(),
            payment.getFailedAt()
        );
    }

    public static PaymentConfirmResult toPaymentConfirmResult(Payment payment) {
        return new PaymentConfirmResult(
            payment.getOrderId(),
            payment.getAmount(),
            payment.getPaymentMethod(),
            payment.getPaymentKey(),
            payment.getApprovedAt()
        );
    }

    public static PaymentCancelResult toPaymentCancelResult(Payment payment) {
        return new PaymentCancelResult(
            payment.getOrderId(),
            payment.getStatus().name(),
            payment.getAmount(),
            payment.getCancelReason(),
            payment.getCanceledAt()
        );
    }
}
