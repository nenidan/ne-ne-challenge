package com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentSearchCommand {

    private Long cursor;

    private int size;

    private String status;

    private LocalDate startDate;

    private LocalDate endDate;
}
