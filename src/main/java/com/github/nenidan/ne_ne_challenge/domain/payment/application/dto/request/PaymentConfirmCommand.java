package com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class PaymentConfirmCommand {

    private String paymentKey;

    private String orderId;

    private int amount;
}
