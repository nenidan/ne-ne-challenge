package com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentSearchResult {

    private Long paymentId;

    private String orderId;

    private int amount;

    private String orderName;

    private String paymentStatus;

    private String paymentMethod;

    private LocalDateTime approvedAt;

    private LocalDateTime failedAt;
}
