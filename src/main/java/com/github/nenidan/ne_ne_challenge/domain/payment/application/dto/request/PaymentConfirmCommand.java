package com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentConfirmCommand {

    private String paymentKey;

    private String orderId;

    private int amount;
}
