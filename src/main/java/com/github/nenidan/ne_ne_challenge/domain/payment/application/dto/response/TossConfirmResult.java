package com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TossConfirmResult {
    private String paymentKey;
    private String orderId;
    private String status;
    private String method;
    private String orderName;
    private String requestedAt;
    private String approvedAt;
    private int totalAmount;
}