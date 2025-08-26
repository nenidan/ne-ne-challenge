package com.github.nenidan.ne_ne_challenge.domain.payment.infrastructure.client.dto.response;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TossCancelResponse {

    private String orderId;              // → PaymentCancelResponse.orderId
    private String status;               // → PaymentCancelResponse.status
    private int totalAmount;             // → PaymentCancelResponse.refundAmount
    private OffsetDateTime approvedAt;           // → PaymentCancelResponse.canceledAt (변환 필요)
}
