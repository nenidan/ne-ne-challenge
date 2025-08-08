package com.github.nenidan.ne_ne_challenge.domain.admin.application.client;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out.PaymentDto;

public interface PaymentClientPort {
    List<PaymentDto> getAllPayments();
}
