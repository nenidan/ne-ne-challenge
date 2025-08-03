package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentCancelResponse {

    private String orderId;

    private String status;

    private int refundAmount; // 환불 금액

    private String cancelReason;

    private LocalDateTime canceledAt;
}
