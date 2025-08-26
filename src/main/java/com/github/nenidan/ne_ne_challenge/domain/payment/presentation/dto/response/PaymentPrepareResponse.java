package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentPrepareResponse {

    @Schema(description = "결제 금액", example = "10000")
    private int amount;

    @Schema(description = "주문 ID (UUID)", example = "order-80d814f7-3a43-4986-a47b-7e89c932680a")
    private String orderId;

    @Schema(description = "주문 상품 이름", example = "포인트 10000원 충전")
    private String orderName;
}

