package com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response;

import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.payment.domain.type.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentStatisticsResult {

    private Long id;

    private Long userId;

    private int amount;

    private String paymentMethod;

    private String paymentKey;

    private String orderId;

    private PaymentStatus status;

    private String cancelReason;

    private LocalDateTime requestedAt;

    private LocalDateTime approvedAt;

    private LocalDateTime failedAt;

    private LocalDateTime canceledAt;
}
