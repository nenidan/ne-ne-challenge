package com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentConfirmResult {

    private String orderId;
    private int amount;
    private String method;
    private String status;
    private LocalDateTime approvedAt;
}
