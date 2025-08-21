package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentPrepareRequest {

    @Schema(description = "결제 금액", example = "10000")
    @Min(value = 10000, message = "최소 결제 금액은 10,000원 입니다.")
    @Max(value = 100000, message = "최대 결제 금액은 100,000원 입니다.")
    private int amount;
}
