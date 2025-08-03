package com.github.nenidan.ne_ne_challenge.domain.payment.application.client;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.TossCancelResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.TossConfirmResult;

public interface TossClient {

    TossConfirmResult confirmPayment(String paymentKey, String orderId, int amount);

    TossCancelResult cancelPayment(String paymentKey, String cancelReason);
}
