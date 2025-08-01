package com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentPrepareCommand {

    private int amount;
}
