package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentPrepareResponse {

    private int amount;

    private String orderId;

    private String orderName;
}

