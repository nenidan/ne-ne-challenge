package com.github.nenidan.ne_ne_challenge.domain.payment.application.client;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.TossClientResult;

public interface TossClient {

    TossClientResult confirmPayment(String paymentKey, String orderId, int amount);
}
