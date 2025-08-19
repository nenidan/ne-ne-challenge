package com.github.nenidan.ne_ne_challenge.domain.payment.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PaymentCompletedEvent {
    private final Long userId;
    private final int amount;
    private final String reason;
    private final String orderId;
}
