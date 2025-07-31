package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.mapper;


import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.ChargePointCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.request.ChargePointRequest;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response.PaymentResponse;

public class PaymentPresentationMapper {

    // ============================ presentation -> application ============================
    public static ChargePointCommand toChargePointCommand(ChargePointRequest chargePointRequest) {
        return new ChargePointCommand(
            chargePointRequest.getAmount(),
            chargePointRequest.getMethod()
        );
    }

    // ============================ application -> presentation============================
    public static PaymentResponse toPaymentResponse(PaymentResult paymentResult) {
        return new PaymentResponse(
            paymentResult.getId(),
            paymentResult.getAmount(),
            paymentResult.getMethod(),
            paymentResult.getStatus(),
            paymentResult.getProcessedAt()
        );
    }
}
