package com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChargePointCommand {
    private int amount;
    private String method;
}

