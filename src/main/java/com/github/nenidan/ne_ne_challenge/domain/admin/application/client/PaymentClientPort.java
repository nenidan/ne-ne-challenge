package com.github.nenidan.ne_ne_challenge.domain.admin.application.client;

import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out.PaymentDto;

import java.util.List;

public interface PaymentClientPort {
    List<PaymentDto> getAllPayments();
}
