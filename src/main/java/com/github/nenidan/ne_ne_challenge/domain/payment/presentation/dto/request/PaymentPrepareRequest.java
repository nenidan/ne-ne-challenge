package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PaymentPrepareRequest {

    @NotNull(message = "충전 금액은 필수입니다")
    @Min(value = 1000, message = "최소 충전 금액은 1,000원입니다")
    @Max(value = 1000000, message = "최대 충전 금액은 1,000,000원입니다")
    private int amount;
}
