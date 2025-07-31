package com.github.nenidan.ne_ne_challenge.domain.payment.domain.type;

import java.util.Arrays;

import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentException;

public enum PaymentStatus {
    PENDING, SUCCESS, FAIL;

    public static PaymentStatus of(String status) {
        return Arrays.stream(PaymentStatus.values())
            .filter(r -> r.name().equalsIgnoreCase(status))
            .findFirst()
            .orElseThrow(() -> new PaymentException(PaymentErrorCode.INVALID_PAYMENT_STATUS));
    }
}
