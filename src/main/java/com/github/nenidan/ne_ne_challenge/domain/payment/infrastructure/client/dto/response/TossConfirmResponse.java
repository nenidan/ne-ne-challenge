package com.github.nenidan.ne_ne_challenge.domain.payment.infrastructure.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TossConfirmResponse {
    private String paymentKey;
    private String orderId;
    private String status;
    private String method;
    private String orderName;
    private int totalAmount;
    private String requestedAt;
    private String approvedAt;
}
