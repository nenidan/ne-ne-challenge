package com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TossCancelResult {

    private String orderId;              // → PaymentCancelResponse.orderId
    private String status;               // → PaymentCancelResponse.status
    private int totalAmount;             // → PaymentCancelResponse.refundAmount
    private String canceledAt;           // → PaymentCancelResponse.canceledAt (변환 필요)
}
