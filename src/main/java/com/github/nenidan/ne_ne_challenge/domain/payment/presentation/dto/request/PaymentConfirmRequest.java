package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentConfirmRequest {

    @Schema(
        description = "토스 결제 결제 키(토스에서 생성)",
        example = "tviva20250820163347aX8I6"
    )
    @NotBlank(message = "결제 키는 필수입니다")
    private String paymentKey;

    @Schema(description = "주문 ID", example = "order-80d814f7-3a43-4986-a47b-7e89c932680a")
    @NotBlank(message = "주문 ID는 필수입니다")
    private String orderId;

    @Schema(description = "결제 금액", example = "10000")
    @Min(value = 10000, message = "최소 결제 금액은 10,000원입니다")
    @Max(value = 100000, message = "최대 결제 금액은 100,000원입니다")
    private int amount;
}
