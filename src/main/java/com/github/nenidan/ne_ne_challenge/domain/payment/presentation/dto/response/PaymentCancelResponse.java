package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentCancelResponse {

    @Schema(description = "주문 ID", example = "order-80d814f7-3a43-4986-a47b-7e89c932680a")
    private String orderId;

    @Schema(description = "결제 상태", example = "CANCELED")
    private String status;

    @Schema(description = "취소 금액", example = "10000")
    private int refundAmount; // 환불 금액

    @Schema(description = "취소 사유", example = "단순 변심")
    private String cancelReason;

    @Schema(description = "취소 일시", example = "2025-08-20T07:48:05.261Z")
    private LocalDateTime canceledAt;
}
