package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResponse {

    // private Long paymentId;

    private int amount;

    private String method;

    private String status;

    private LocalDateTime processedAt;
}
