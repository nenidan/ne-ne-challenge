package com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class PaymentCancelCommand {

    private String cancelReason;
}
