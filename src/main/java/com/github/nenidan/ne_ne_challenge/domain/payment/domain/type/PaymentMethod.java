package com.github.nenidan.ne_ne_challenge.domain.payment.domain.type;

import java.util.Arrays;

import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentException;

public enum PaymentMethod {
    MOCK;

    public static PaymentMethod of(String method) {
        return Arrays.stream(PaymentMethod.values())
            .filter(r -> r.name().equalsIgnoreCase(method))
            .findFirst()
            .orElseThrow(() -> new PaymentException(PaymentErrorCode.INVALID_PAYMENT_METHOD));
    }
}
