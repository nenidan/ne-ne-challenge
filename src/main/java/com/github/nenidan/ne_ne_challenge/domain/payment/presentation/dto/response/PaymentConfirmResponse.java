package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentConfirmResponse {

    private String orderId;
    private int amount;
    private String method;
    private String status;
    private String orderName;
    private LocalDateTime approvedAt;
}
