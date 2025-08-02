package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PaymentConfirmRequest {

    @NotBlank(message = "결제 키는 필수입니다")
    private String paymentKey;

    @NotBlank(message = "주문 ID는 필수입니다")
    private String orderId;

    @Min(value = 1000, message = "최소 결제 금액은 1,000원입니다")
    @Max(value = 1000000, message = "최대 결제 금액은 1,000,000원입니다")
    private int amount;
}
