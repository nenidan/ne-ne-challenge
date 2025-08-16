package com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.vo;

import java.util.UUID;

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
public class OrderId {

    @Column(name = "order_id")
    private String value;

    private OrderId(String value) {
        validateOrderId(value);
        this.value = value;
    }

    public static OrderId generate() {
        return new OrderId("order-" + UUID.randomUUID());
    }

    public static OrderId of(String orderId) {
        return new OrderId(orderId);
    }

    private void validateOrderId(String orderId) {
        if (orderId == null || !orderId.startsWith("order-")) {
            throw new PaymentException(PaymentErrorCode.INVALID_ORDER_ID);
        }
    }
}
