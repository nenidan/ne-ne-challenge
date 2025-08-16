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
public class OrderName {

    @Column(name = "order_name")
    private String value;

    private OrderName(String value) {
        this.value = value;
    }

    public static OrderName forPointCharge(Money amount) {
        return new OrderName("포인트 " + amount.getValue() + "원 충전");
    }
}
