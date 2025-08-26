package com.github.nenidan.ne_ne_challenge.domain.payment.application.mapper;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentCancelResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentConfirmResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentPrepareResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentSearchResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentStatisticsResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.Payment;

public class PaymentApplicationMapper {

    public static PaymentPrepareResult toPaymentPrepareResult(Payment payment) {
        return new PaymentPrepareResult(
            payment.getAmount().getValue(),
            payment.getOrderId().getValue(),
            payment.getOrderName().getValue()
        );
    }

    public static PaymentSearchResult toPaymentResult(Payment payment) {
        return new PaymentSearchResult(
            payment.getId(),
            payment.getOrderId().getValue(),
            payment.getAmount().getValue(),
            payment.getStatus().name(),
            payment.getPaymentMethod(),
            payment.getApprovedAt(),
            payment.getFailedAt()
        );
    }

    public static PaymentConfirmResult toPaymentConfirmResult(Payment payment) {
        return new PaymentConfirmResult(
            payment.getOrderId().getValue(),
            payment.getAmount().getValue(),
            payment.getPaymentMethod(),
            payment.getStatus().name(),
            payment.getApprovedAt()
        );
    }

    public static PaymentCancelResult toPaymentCancelResult(Payment payment) {
        return new PaymentCancelResult(
            payment.getOrderId().getValue(),
            payment.getStatus().name(),
            payment.getPaymentKey().getValue(),
            payment.getAmount().getValue(),
            payment.getCancelReason(),
            payment.getCanceledAt()
        );
    }

    public static PaymentStatisticsResult toPaymentStatisticsResult(Payment payment) {
        return new PaymentStatisticsResult(
            payment.getId(),
            payment.getUserId(),
            payment.getAmount().getValue(),
            payment.getPaymentMethod(),
            payment.getPaymentKey().getValue(),
            payment.getOrderId().getValue(),
            payment.getStatus(),
            payment.getCancelReason(),
            payment.getRequestedAt(),
            payment.getApprovedAt(),
            payment.getFailedAt(),
            payment.getCanceledAt()
        );
    }
}
