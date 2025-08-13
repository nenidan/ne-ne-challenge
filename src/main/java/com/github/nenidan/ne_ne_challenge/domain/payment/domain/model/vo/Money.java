package com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.vo;

import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentException;

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
public class Money {

    @Column(name = "amount", nullable = false)
    private int value;

    private Money(int value) {
        validateAmount(value);
        this.value = value;
    }

    public static Money of(int amount) {
        return new Money(amount);
    }

    public static Money ofPayment(int amount) {
        if (amount < 10000 || amount > 100000) {
            throw new PaymentException(PaymentErrorCode.INVALID_PAYMENT_AMOUNT);
        }

        return new Money(amount);
    }

    private void validateAmount(int amount) {
        if (amount <= 0) {
            throw new PaymentException(PaymentErrorCode.INVALID_AMOUNT);
        }
    }
}
