package com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentPrepareResult {

    private int amount;
    private String orderId;
    private String orderName;
}
