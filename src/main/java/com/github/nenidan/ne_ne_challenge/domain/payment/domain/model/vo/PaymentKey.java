package com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentKey {

    @Column(name = "payment_key", nullable = true)
    private String value;

    private PaymentKey(String value) {
        this.value = value;
    }

    public static PaymentKey of(String paymentKey) {
        return new PaymentKey(paymentKey);
    }
}
