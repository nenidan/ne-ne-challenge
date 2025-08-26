package com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TossCancelResult {

    private String orderId;              // → PaymentCancelResponse.orderId
    private String status;               // → PaymentCancelResponse.status
    private int totalAmount;             // → PaymentCancelResponse.refundAmount
    private OffsetDateTime canceledAt;           // → PaymentCancelResponse.canceledAt (변환 필요)
}
