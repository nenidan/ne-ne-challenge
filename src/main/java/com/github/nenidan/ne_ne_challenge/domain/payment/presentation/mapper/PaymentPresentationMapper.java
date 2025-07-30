package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.mapper;


import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PaymentConfirmCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PaymentPrepareCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentConfirmResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentPrepareResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.request.PaymentConfirmRequest;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.request.PaymentPrepareRequest;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response.PaymentConfirmResponse;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response.PaymentPrepareResponse;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response.PaymentResponse;

public class PaymentPresentationMapper {

    // ============================ presentation -> application ============================
    public static PaymentPrepareCommand toPaymentPrepareCommand(PaymentPrepareRequest request) {
        return new PaymentPrepareCommand(
            request.getAmount()
        );
    }

    public static PaymentConfirmCommand toPaymentConfirmCommand(PaymentConfirmRequest request) {
        return new PaymentConfirmCommand(
            request.getPaymentKey(),
            request.getOrderId(),
            request.getAmount()
        );
    }

    // ============================ application -> presentation============================
    public static PaymentResponse toPaymentResponse(PaymentResult paymentResult) {
        return new PaymentResponse(
            // paymentResult.getOrderId(),
            paymentResult.getAmount(),
            paymentResult.getMethod(),
            paymentResult.getStatus(),
            paymentResult.getProcessedAt()
        );
    }

    public static PaymentConfirmResponse toPaymentConfirmResponse(PaymentConfirmResult result) {
        return new PaymentConfirmResponse(
            result.getOrderId(),
            result.getAmount(),
            result.getMethod(),
            result.getStatus(),
            result.getOrderName(),
            result.getApprovedAt()
        );
    }

    public static PaymentPrepareResponse toPaymentPrepareResponse(PaymentPrepareResult result) {
        return new PaymentPrepareResponse(
            result.getAmount(),
            result.getOrderId(),
            result.getOrderName()
        );
    }

}
