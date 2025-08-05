package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentSearchResponse {

    private Long paymentId;

    private String orderId;

    private int amount;

    private String paymentStatus;

    private String paymentMethod;

    private LocalDateTime approvedAt;

    private LocalDateTime failedAt;
}
