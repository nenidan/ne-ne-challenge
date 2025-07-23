package com.github.nenidan.ne_ne_challenge.domain.payment.dto.response;

import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.payment.entity.Payment;
import com.github.nenidan.ne_ne_challenge.domain.payment.type.PaymentMethod;
import com.github.nenidan.ne_ne_challenge.domain.payment.type.PaymentStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentResponse {

    private Long paymentId;

    private int amount;

    private PaymentMethod method;

    private PaymentStatus status;

    private LocalDateTime processedAt;

    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
            payment.getId(),
            payment.getAmount(),
            payment.getMethod(),
            payment.getStatus(),
            payment.getConfirmedAt() != null ? payment.getConfirmedAt() : payment.getFailedAt());
    }
}
