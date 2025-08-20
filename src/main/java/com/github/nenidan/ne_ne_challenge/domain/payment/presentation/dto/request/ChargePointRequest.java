package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChargePointRequest {

    @Min(value = 1000, message = "최소 충전 금액은 1000원 이상이어야 합니다.")
    private int amount;

    @NotNull(message = "결제 수단을 입력해주세요.")
    private String method;
}
