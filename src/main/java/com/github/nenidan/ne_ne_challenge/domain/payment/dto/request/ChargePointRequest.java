package com.github.nenidan.ne_ne_challenge.domain.payment.dto.request;

import com.github.nenidan.ne_ne_challenge.domain.payment.type.PaymentMethod;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChargePointRequest {

    @Min(value = 1000, message = "최소 충전 금액은 1000원 이상이어야 합니다.")
    private int amount;

    @NotNull(message = "결제 수단을 입력해주세요.")
    private PaymentMethod method;
}
