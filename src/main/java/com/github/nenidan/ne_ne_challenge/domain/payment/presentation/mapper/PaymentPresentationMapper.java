package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.mapper;


import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PaymentConfirmCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PaymentPrepareCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PaymentSearchCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentConfirmResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentPrepareResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentSearchResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.request.PaymentConfirmRequest;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.request.PaymentPrepareRequest;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.request.PaymentSearchRequest;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response.PaymentConfirmResponse;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response.PaymentPrepareResponse;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response.PaymentSearchResponse;

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

    public static PaymentSearchCommand toPaymentSearchCommand(PaymentSearchRequest request) {
        return new PaymentSearchCommand(
            request.getCursor(),
            request.getSize(),
            request.getStatus(),
            request.getStartDate(),
            request.getEndDate()
        );
    }

    // ============================ application -> presentation============================
    public static PaymentSearchResponse toPaymentResponse(PaymentSearchResult paymentSearchResult) {
        return new PaymentSearchResponse(
            paymentSearchResult.getPaymentId(),
            paymentSearchResult.getOrderId(),
            paymentSearchResult.getAmount(),
            paymentSearchResult.getOrderName(),
            paymentSearchResult.getPaymentStatus(),
            paymentSearchResult.getPaymentMethod(),
            paymentSearchResult.getApprovedAt(),
            paymentSearchResult.getFailedAt()
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
